package com.github.ligangty.undertow.resteasy.test.rest;

import com.github.ligangty.undertow.resteasy.test.instance.RestProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class SimpleResourceDynamicFeature
        implements DynamicFeature, RestProvider
{
    private final Logger logger = LoggerFactory.getLogger( getClass() );

    @Override
    public void configure( ResourceInfo resourceInfo, FeatureContext context )
    {
        logger.debug( "Entering dynamic filter." );
        if ( SimpleResource.class.equals( resourceInfo.getResourceClass() ) && resourceInfo.getResourceMethod()
                                                                                           .getName()
                                                                                           .contains( "getOutput" ) )
        {
            logger.debug( "Adding for SimpleResource.getOutput." );
            context.register( SimpleResourceResponseRewriteFilter.class );
        }
    }
}
