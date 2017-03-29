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


package demo.server;

import java.io.IOException;
import java.io.InputStream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.errai.marshalling.server.MappingContextSingleton;
import org.jboss.errai.marshalling.server.ServerMarshalling;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.slf4j.Logger;

import demo.client.shared.builtin.FlowService;

@ApplicationScoped
public class FlowServiceImpl implements FlowService {

    static {
        // Has to happen before we try to unmarshall something or things get nasty
        MappingContextSingleton.get();
    }

    @Inject
    private Logger logger;

    @Override
    public Graph<?, Node> lookupFlowGraph( final String name ) {
        final ClassLoader cl = getClass().getClassLoader();
        final String fileName = name + ".appflow";
        final InputStream is = cl.getResourceAsStream( fileName );
        if ( is != null ) {
            try {
                final Graph graph = (Graph) ServerMarshalling.fromJSON( is );
                logger.info( "Successfully deserialized graph in [{}]", fileName );
                return graph;
            } catch ( final IOException e ) {
                logger.error( String.format( "Could not unmarshall diagram for [%s]", fileName ), e );
                return null;
            }
        }
        else {
            logger.info( "File [{}] was not found.", fileName );
            return null;
        }
    }

}
