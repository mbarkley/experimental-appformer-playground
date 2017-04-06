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

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.jboss.errai.common.client.api.IsElement;
import org.kie.appformer.flow.api.Displayer;
import org.kie.appformer.flow.api.UIComponent;

@Dependent
public class FormStepDisplayer implements Displayer<IsElement> {

    @Inject
    private Event<IsElement> event;

    @Override
    public void show( final UIComponent<?, ?, IsElement> uiComponent ) {
        event.fire( uiComponent.asComponent() );
    }

    @Override
    public void hide( final UIComponent<?, ?, IsElement> uiComponent ) {
        removeFromParent( uiComponent.asComponent().getElement() );
    }

}
