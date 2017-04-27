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

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.jboss.errai.ioc.client.api.ManagedInstance;
import org.kie.appformer.flow.api.AppFlowFactory;
import org.kie.appformer.flow.api.Command;
import org.kie.appformer.flow.api.CrudOperation;
import org.kie.appformer.flow.api.UIComponent;
import org.kie.appformer.flow.api.Unit;
import org.kie.appformer.flow.lang.CompilationContext;
import org.kie.appformer.flow.lang.Compiler;
import org.kie.appformer.flow.lang.Parser;
import org.kie.appformer.flow.lang.SimplePatternMatcher;
import org.kie.appformer.flow.lang.Try;
import org.kie.appformer.formmodeler.rendering.client.flow.FlowProducer;
import org.kie.appformer.formmodeler.rendering.client.view.ListView;

@Singleton
public class FlowCompilerProducer {

    @Inject private ManagedInstance<FlowProducer<?, ?, ?, ?, ?>> flowProducerProvider;
    @Inject private AppFlowFactory factory;

    @Produces private CompilationContext context;
    @Produces private Compiler compiler;
    @Produces private Parser parser;

    @PostConstruct
    private void init() {
        final CompilationContext.Builder builder = new CompilationContext.Builder( null );
        for ( final FlowProducer<?, ?, ?, ?, ?> producer : flowProducerProvider ) {
            final String entity = producer.getModelType().getSimpleName();
            final String formModel = producer.getFormModelType().getSimpleName();
            final String flowDataProvider = "FlowDataProvider<" + entity + ">";
            final String unit = "Unit";

            builder.addFlow( "Save" + entity, entity, entity, factory.buildFromStep( producer.save() ) );
            builder.addFlow( "Update" + entity, entity, entity, factory.buildFromStep( producer.update() ) );
            builder.addFlow( "Delete" + entity, entity, entity, factory.buildFromStep( producer.delete() ) );
            builder.addFlow( "Load" + entity, unit, flowDataProvider, factory.buildFromStep( producer.load() ) );

            builder.addFlow( entity + "To" + formModel, entity, formModel, factory.buildFromFunction( producer::modelToFormModel ) );
            builder.addFlow( formModel + "To" + entity, formModel, entity, factory.buildFromFunction( producer::formModelToModel ) );
            builder.addFlow( "New" + entity, unit, entity, factory.buildFromSupplier( producer::newModel ) );
            builder.addFlow( "New" + formModel, unit, formModel, factory.buildFromSupplier( producer::newFormModel ) );

            final String listViewName = entity + "ListView";
            builder.addFlowConstructor( listViewName, flowDataProvider, "Command<CrudOperation," + entity + ">", ctx -> {
                final boolean create = Boolean.parseBoolean( ctx.getOrDefault( "create", "true" ).toString() );
                final boolean edit = Boolean.parseBoolean( ctx.getOrDefault( "edit", "true" ).toString() );
                final boolean delete = Boolean.parseBoolean( ctx.getOrDefault( "delete", "true" ).toString() );
                final String display = ctx.getOrDefault( "display", "main" ).toString();

                if ( "main".equals( display ) ) {
                    final UIComponent<?, ?, ? extends ListView<?, ?>> component = producer.listView( create, edit, delete );
                    return Try.success( factory.buildFromStep( producer.displayMain( component ) ) );
                }
                else {
                    throw new RuntimeException( "Display [" + display + "] not supported!" );
                }
            } );

            final String formViewName = entity + "FormView";
            builder.addFlowConstructor( formViewName, formModel, "Optional<" + formModel + ">", ctx -> {
                final String display = ctx.getOrDefault( "display", "main" ).toString();

                if ( "main".equals( display ) ) {
                    return Try.success( producer.displayMainStandaloneForm() );
                }
                else if ( "modal".equals( display ) ) {
                    return Try.success( factory.buildFromStep( producer.displayModalForm() ) );
                }
                else {
                    throw new RuntimeException( "Display [" + display + "] not supported!" );
                }
            } );
        }

        builder.addFlow( "toUnit", "?", "Unit", factory.buildFromFunction( o -> Unit.INSTANCE ) );
        builder.addValue( "unit", "Unit", Unit.INSTANCE );

        builder.addMatcher( "Command",
                            new SimplePatternMatcher<Command<?, ?>>(
                                    o -> ( o instanceof Command ? Optional.of( (Command<?, ?>) o ) : Optional.empty() ),
                                    asList( cmd -> cmd.commandType, cmd -> cmd.value ) ) );
        builder.addMatcher( "Some",
                            new SimplePatternMatcher<Optional<?>>(
                                    o -> ( o instanceof Optional && ((Optional<?>) o).isPresent() ? Optional.of( (Optional<?>) o ) : Optional.empty() ),
                                    asList( some -> some.get() ) ) );
        builder.addMatcher( "None",
                            new SimplePatternMatcher<Optional<?>>(
                                    o -> ( o instanceof Optional && !((Optional<?>) o).isPresent() ? Optional.of( (Optional<?>) o ) : Optional.empty() ),
                                    emptyList() ) );

        builder.addValue( "CREATE", "CrudOperation", CrudOperation.CREATE );
        builder.addValue( "UPDATE", "CrudOperation", CrudOperation.UPDATE );
        builder.addValue( "DELETE", "CrudOperation", CrudOperation.DELETE );

        context = builder.build();

        parser = new Parser();
        compiler = new Compiler( parser, context, factory );
    }

}
