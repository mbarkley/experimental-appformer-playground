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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.inject.Inject;

import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.kie.appformer.flow.api.AppFlow;
import org.kie.appformer.flow.api.AppFlowFactory;
import org.kie.appformer.flow.api.Unit;
import org.kie.appformer.flow.lang.Compiler;
import org.kie.appformer.flow.lang.Try;
import org.slf4j.Logger;

import com.google.gwt.user.client.Window;

import demo.client.shared.builtin.FlowService;

@EntryPoint
public class FlowLoader {

    @Inject
    private Logger logger;

    @Inject
    private Caller<FlowService> flowService;

    @Inject
    private Compiler compiler;

    @Inject
    private AppFlowFactory factory;

    private final Map<String, AppFlow<?, ?>> exportedFlows = new HashMap<>();

    private final List<Consumer<AppFlow<Unit, ?>>> queuedCallbacks = new ArrayList<>();
    private Consumer<Consumer<AppFlow<Unit, ?>>> callbackHandler = cb -> queuedCallbacks.add( cb );

    @AfterInitialization
    private void init() {
        flowService.call( ( final String source ) -> {
            if ( source != null ) {
                final Try<List<String>, Map<String, AppFlow<?, ?>>> compileResult = compiler.compileFlows( source );
                if ( compileResult.failure().isPresent() ) {
                    Window.alert( "Unable to compile source from flow service. Check console for details." );
                    final String problems = compileResult
                        .failure()
                        .get()
                        .stream()
                        .reduce( (s1, s2) -> s1 + "\n" + s2 )
                        .orElse( "" );

                    logger.error( problems );
                }
                else {
                    exportedFlows.putAll( compileResult.success().get() );
                }
            }
            else {
                logger.error( "No flow was provided by the flow service." );
            }

            callbackHandler = cb -> {
                if ( exportedFlows.containsKey( "Main" ) ) {
                    cb.accept( (AppFlow<Unit, ?>) exportedFlows.get( "Main" ) );
                }
                else {
                    cb.accept( buildAlertFlow() );
                }
            };

            queuedCallbacks.forEach( callbackHandler::accept );
            queuedCallbacks.clear();
        } ).getMainFlow();
    }

    public void getMainFlow( final Consumer<AppFlow<Unit, ?>> callback ) {
        callbackHandler.accept( callback );
    }

    private AppFlow<Unit, Unit> buildAlertFlow() {
        return factory.buildFromFunction( ( final Unit u ) -> {
            Window.alert( "No [Main] flow was exported. See console for possible errors." );
            return Unit.INSTANCE;
        } );
    }
}
