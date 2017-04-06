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

import javax.enterprise.context.Dependent;

import org.jboss.errai.databinding.client.BindableProxy;
import org.jboss.errai.databinding.client.BindableProxyFactory;
import org.jboss.errai.databinding.client.PropertyType;
import org.kie.appformer.flowset.interpreter.ModelOracle;

@Dependent
public class BindableModelOracle implements ModelOracle {

    @Override
    public void setProperty( final Object model,
                             final String property,
                             final Object value ) {
        final BindableProxy<?> proxy = getProxy( model );
        final int dot = property.indexOf( '.' );
        if ( dot == -1 ) {
            proxy.set( property, value );
        } else {
            setProperty( createNestedModel( model, property.substring( 0, dot ) ), property.substring( dot+1 ), value );
        }
    }

    @Override
    public Object getProperty( final Object model,
                               final String property ) {
        final BindableProxy<?> proxy = getProxy( model );
        final int dot = property.indexOf( '.' );
        if ( dot == -1 ) {
            return proxy.get( property );
        }
        else {
            final Object submodel = getProperty( model, property.substring( 0, dot ) );
            return submodel != null ? getProperty( submodel, property.substring( dot+1 ) ) : null;
        }
    }

    @Override
    public Object createNestedModel( final Object model,
                                     final String property ) {
        final BindableProxy<?> proxy = getProxy( model );
        final int dot = property.indexOf( '.' );
        if ( dot == -1 ) {
            Object submodel;
            if ( (submodel = proxy.get( property )) == null ) {
                final PropertyType propertyType = proxy.getBeanProperties().get( property );
                submodel = BindableProxyFactory.getBindableProxy( propertyType.getType() );
                proxy.set( property, submodel );
            }
            return submodel;
        }
        else {
            final String directProperty = property.substring( 0, dot );
            final Object submodel = createNestedModel( model, directProperty );
            return createNestedModel( submodel, property.substring( dot+1 ) );
        }
    }

    private BindableProxy<?> getProxy( final Object model ) {
        return (BindableProxy<?>) BindableProxyFactory.getBindableProxy( model );
    }

}
