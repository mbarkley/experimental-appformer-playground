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

import org.apache.commons.io.IOUtils;
import org.jboss.errai.bus.server.annotations.Service;

import demo.client.shared.builtin.FlowService;

@Service
public class FlowServiceImpl implements FlowService {

    @Override
    public String getMainFlow() {
        final InputStream flowFileStream = Thread.currentThread().getContextClassLoader().getResourceAsStream( "/Main.flow" );
        if ( flowFileStream == null ) {
            return null;
        }
        else {
            try {
                return IOUtils.toString( flowFileStream, "UTF-8" );
            }
            catch ( final IOException e ) {
                return null;
            }
            finally {
                IOUtils.closeQuietly( flowFileStream );
            }
        }
    }

}
