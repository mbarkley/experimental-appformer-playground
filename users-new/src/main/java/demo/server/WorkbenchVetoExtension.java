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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkbenchVetoExtension implements Extension {

    private static final Logger logger = LoggerFactory.getLogger( WorkbenchVetoExtension.class );

    private static final String[] prefices = new String[] {
                                                           "org.kie.workbench",
                                                           "org.uberfire",
                                                           "org.kie.appformer.flowset"
    };
    private static final Set<String> matches = new HashSet<>(Arrays.asList(
    ));

    public void vetoWorkbenchBeans( @Observes final ProcessAnnotatedType<?> annotatedType ) {
        final String packageName = annotatedType.getAnnotatedType().getJavaClass().getName();
        if ( isBanned( packageName ) ) {
            logger.info( "Vetoing {}", annotatedType.getAnnotatedType().getJavaClass().getName() );
            annotatedType.veto();
        }
    }

    private boolean isBanned( final String className ) {
        return matches.contains( className )
                || Arrays.stream( prefices ).anyMatch( p -> className.startsWith( p ) );
    }

}
