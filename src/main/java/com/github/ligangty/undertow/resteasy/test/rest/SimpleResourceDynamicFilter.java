/**
 * Copyright (C) 2013 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.ligangty.undertow.resteasy.test.rest;

import com.github.ligangty.undertow.resteasy.test.instance.RestProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

//@Provider
//@ApplicationScoped
public class SimpleResourceDynamicFilter
        implements DynamicFeature, RestProvider
{
    @Override
    public void configure( ResourceInfo resourceInfo, FeatureContext context )
    {

    }
}
