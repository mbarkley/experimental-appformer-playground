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


package demo.client.shared.builtin;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.kie.workbench.common.stunner.core.diagram.Metadata;
import org.uberfire.backend.vfs.Path;

/**
 * Without a portable impl of Metadata, compilation fails.
 */
@Portable
public class WorkaroundMetadata implements Metadata {

    @Override
    public String getDefinitionSetId() {
        // TODO Auto-generated method stub
        throw new RuntimeException( "Not yet implemented." );
    }

    @Override
    public String getTitle() {
        // TODO Auto-generated method stub
        throw new RuntimeException( "Not yet implemented." );
    }

    @Override
    public void setTitle( final String title ) {
        // TODO Auto-generated method stub
        throw new RuntimeException( "Not yet implemented." );
    }

    @Override
    public String getShapeSetId() {
        // TODO Auto-generated method stub
        throw new RuntimeException( "Not yet implemented." );
    }

    @Override
    public void setShapeSetId( final String id ) {
        // TODO Auto-generated method stub
        throw new RuntimeException( "Not yet implemented." );
    }

    @Override
    public String getCanvasRootUUID() {
        // TODO Auto-generated method stub
        throw new RuntimeException( "Not yet implemented." );
    }

    @Override
    public void setCanvasRootUUID( final String uuid ) {
        // TODO Auto-generated method stub
        throw new RuntimeException( "Not yet implemented." );
    }

    @Override
    public String getThumbData() {
        // TODO Auto-generated method stub
        throw new RuntimeException( "Not yet implemented." );
    }

    @Override
    public void setThumbData( final String data ) {
        // TODO Auto-generated method stub
        throw new RuntimeException( "Not yet implemented." );
    }

    @Override
    public Path getPath() {
        // TODO Auto-generated method stub
        throw new RuntimeException( "Not yet implemented." );
    }

    @Override
    public void setPath( final Path path ) {
        // TODO Auto-generated method stub
        throw new RuntimeException( "Not yet implemented." );
    }

    @Override
    public Class<? extends Metadata> getMetadataType() {
        // TODO Auto-generated method stub
        throw new RuntimeException( "Not yet implemented." );
    }

}
