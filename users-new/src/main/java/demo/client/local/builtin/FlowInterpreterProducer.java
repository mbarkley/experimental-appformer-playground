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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.jboss.errai.ioc.client.api.ManagedInstance;
import org.kie.appformer.flow.api.AppFlow;
import org.kie.appformer.flow.api.AppFlowFactory;
import org.kie.appformer.flow.api.Command;
import org.kie.appformer.flow.api.CrudOperation;
import org.kie.appformer.flow.api.FormOperation;
import org.kie.appformer.flow.api.Step;
import org.kie.appformer.flow.api.Unit;
import org.kie.appformer.flowset.interpreter.Interpreter;
import org.kie.appformer.formmodeler.rendering.client.flow.FlowProducer;
import org.kie.appformer.formmodeler.rendering.client.shared.FormModel;

@Singleton
public class FlowInterpreterProducer {

    @Inject
    private ManagedInstance<FlowProducer<?, ?, ?, ?, ?>> flowProducerProvider;
    @Inject
    private AppFlowFactory                               factory;

    @Produces
    private Interpreter                                  interpreter;

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    @PostConstruct
    private void init() {
        final Map<String, AppFlow<?, ?>> context = new HashMap<>();
        for ( final FlowProducer<?, ?, ?, ?, ?> producer : flowProducerProvider ) {
            final String entity = producer.getModelType().getSimpleName();

            context.put( "Save" + entity,
                         factory.buildFromStep( producer.save() ) );
            context.put( "Update" + entity,
                         factory.buildFromStep( producer.update() ) );
            context.put( "Delete" + entity,
                         factory.buildFromStep( producer.delete() ) );
            context.put( "Load" + entity,
                         factory.buildFromStep( producer.load() ) );

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
        }

        context.put( "toUnit",
                     factory.buildFromFunction( o -> Unit.INSTANCE ) );
        context.put( "unit",
                     factory.buildFromConstant( Unit.INSTANCE ) );

        interpreter = new Interpreter( context,
                                       new HashSet<>( Arrays.asList( CrudOperation.class, FormOperation.class ) ),
                                       factory );
    }

}
