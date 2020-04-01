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

import com.github.ligangty.undertow.resteasy.test.instance.Resources;
import com.github.ligangty.undertow.resteasy.test.instance.RestProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
@SimpleResourceBinding
public class SimpleResourceResponseRewriteFilter
        implements ContainerResponseFilter, RestProvider
{
    private final Logger logger = LoggerFactory.getLogger( getClass() );

    @Override
    public void filter( ContainerRequestContext requestContext, ContainerResponseContext responseContext )
    {
        logger.info( "This filter should only work for /api/simple/output!" );
    }

}