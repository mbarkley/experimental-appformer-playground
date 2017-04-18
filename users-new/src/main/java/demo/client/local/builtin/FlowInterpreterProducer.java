/*
 * Copyright (C) 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package demo.client.local.builtin;

import static org.jboss.errai.common.client.dom.DOMUtil.removeFromParent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.jboss.errai.common.client.api.IsElement;
import org.jboss.errai.ioc.client.api.ManagedInstance;
import org.kie.appformer.flow.api.AppFlow;
import org.kie.appformer.flow.api.AppFlowFactory;
import org.kie.appformer.flow.api.Command;
import org.kie.appformer.flow.api.CrudOperation;
import org.kie.appformer.flow.api.Displayer;
import org.kie.appformer.flow.api.FormOperation;
import org.kie.appformer.flow.api.Step;
import org.kie.appformer.flow.api.UIComponent;
import org.kie.appformer.flow.api.Unit;
import org.kie.appformer.flowset.interpreter.Interpreter;
import org.kie.appformer.flowset.interpreter.ModelOracle;
import org.kie.appformer.formmodeler.rendering.client.flow.FlowProducer;
import org.kie.appformer.formmodeler.rendering.client.shared.FormModel;
import org.kie.appformer.formmodeler.rendering.client.view.FormStepWrapper;

@Singleton
public class FlowInterpreterProducer {

    @Inject
    private ManagedInstance<FlowProducer<?, ?, ?, ?, ?>> flowProducerProvider;

    @Inject
    private AppFlowFactory                               factory;

    @Inject
    private ModelOracle                                  modelOracle;

    @Inject
    private Event<IsElement>                             event;

    @Produces
    private Interpreter<FormStepWrapper<?, ?, ?>>        interpreter;

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    @PostConstruct
    private void init() {
        final Map<String, AppFlow<?, ?>> context = new HashMap<>();
        final Map<String, Supplier<UIComponent<?, ? extends Command<?, ?>, ? extends FormStepWrapper<?, ?, ?>>>> formSteps = new HashMap<>();
        for ( final FlowProducer<?, ?, ?, ?, ?> producer : flowProducerProvider ) {
            final String entity = producer.getModelType().getSimpleName();

            context.put( "Save" + entity,
                         factory.buildFromStep( producer.save() ) );
            context.put( "Update" + entity,
                         factory.buildFromStep( producer.update() ) );
            context.put( "Delete" + entity,
                         factory.buildFromStep( producer.delete() ) );
            context.put( "Load" + entity + "List",
                         factory.buildFromStep( producer.load() ) );
            context.put( "Lookup" + entity,
                          factory.buildFromStep( producer.lookup() ) );

            context.put( "New" + entity,
                         factory.buildFromSupplier( producer::newModel ) );

            final String listViewName = entity + "ListView";
            context.put( listViewName,
                         factory.buildFromTransition( input -> {
                             final Step displayStep = producer.displayMain( producer.listView( true,
                                                                                               true,
                                                                                               true ) );
                             final AppFlow start = factory.buildFromConstant( input );
                             return start.andThen( displayStep );
                         } ) );

            final String formViewName = entity + "FormView";
            context.put( formViewName,
                         factory.buildFromFunction( producer::modelToFormModel )
                                .andThen( (AppFlow) producer.displayMainStandaloneForm() )
                                .andThen( (Function<Command<FormOperation, FormModel>, Object>) c ->
                                    c.map( formModel -> ((FlowProducer) producer).formModelToModel( formModel ) ) ) );

            formSteps.put( entity, producer::formStepView );
        }

        context.put( "toUnit",
                     factory.buildFromFunction( o -> Unit.INSTANCE ) );
        context.put( "unit",
                     factory.buildFromConstant( Unit.INSTANCE ) );

        final Function<String, Optional<? extends UIComponent<?, ? extends Command<?, ?>, ? extends FormStepWrapper<?, ?, ?>>>> stepProvider =
             name -> Optional.ofNullable( formSteps.get( name ) ).map( s -> s.get() );

        interpreter = new Interpreter( context,
                                         stepProvider,
                                         new FormStepDisplayer(),
                                         modelOracle,
                                         new HashSet<>( Arrays.asList( CrudOperation.class,
                                                                       FormOperation.class ) ),
                                         factory );
    }

    private class FormStepDisplayer implements Displayer<FormStepWrapper<?, ?, ?>> {

        @Override
        public void show( final UIComponent<?, ?, FormStepWrapper<?, ?, ?>> uiComponent ) {
            event.fire( uiComponent.asComponent() );
        }

        @Override
        public void hide( final UIComponent<?, ?, FormStepWrapper<?, ?, ?>> uiComponent ) {
            removeFromParent( uiComponent.asComponent().getElement() );
        }

    }

}
