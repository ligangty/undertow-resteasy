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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path( "/api/simple" )
public class SimpleResource
        implements Resources
{
    private final Logger logger = LoggerFactory.getLogger( getClass() );

    @GET
    @Path( "/output" )
    @Produces( "text/plain" )
    @SimpleResourceBinding
    public Response getOutput()
    {
        return Response.ok( "Hello output!" ).build();
    }

    @GET
    @Path( "/print" )
    @Produces( "text/plain" )
    public Response getPrint()
    {
        return Response.ok( "Hello print!" ).build();
    }
}
