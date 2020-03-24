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
