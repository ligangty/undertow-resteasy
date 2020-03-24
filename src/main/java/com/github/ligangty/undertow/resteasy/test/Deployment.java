package com.github.ligangty.undertow.resteasy.test;

import com.github.ligangty.undertow.resteasy.test.instance.Resources;
import com.github.ligangty.undertow.resteasy.test.instance.RestProvider;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.ServletInfo;
import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@ApplicationScoped
public class Deployment
{

    private final Logger logger = LoggerFactory.getLogger( getClass() );

    @Inject
    private Instance<Resources> resourcesInstances;

    @Inject
    private Instance<RestProvider> providerInstances;

    private Set<Class<? extends Resources>> resourceClasses;

    private Set<Class<? extends RestProvider>> providerClasses;

    private Set<Class<?>> classes;

    protected Deployment()
    {
    }

    public Deployment( final Set<Class<? extends Resources>> resourceClasses,
                       final Set<Class<? extends RestProvider>> restProviders )
    {
        this.resourceClasses = resourceClasses;
        this.providerClasses = restProviders;
        this.classes = getClasses();
    }

    @PostConstruct
    public void cdiInit()
    {
        resourceClasses = new HashSet<>();
        for ( final Resources indyResources : resourcesInstances )
        {
            resourceClasses.add( indyResources.getClass() );
        }

        providerClasses = new HashSet<>();
        for ( final RestProvider restProvider : providerInstances )
        {
            providerClasses.add( restProvider.getClass() );
        }

        classes = getClasses();
    }

    public DeploymentInfo getDeployment( final String contextRoot )
    {
        final ResteasyDeployment deployment = new ResteasyDeployment();

        Application application = new Application()
        {
            @Override
            public Set<Class<?>> getClasses()
            {
                return classes;
            }
        };

        deployment.setApplication( application );

        final ServletInfo resteasyServlet = Servlets.servlet( "REST", HttpServlet30Dispatcher.class )
                                                    .setAsyncSupported( true )
                                                    .setLoadOnStartup( 1 )
                                                    .addMapping( "/api*" )
                                                    .addMapping( "/api/*" );

        logger.info( "App context root: {}", contextRoot );

        return new DeploymentInfo().setContextPath( contextRoot )
                                   .addServletContextAttribute( ResteasyDeployment.class.getName(), deployment )
                                   .addServlet( resteasyServlet )
                                   .setDeploymentName( "rest-test" )
                                   .setClassLoader( ClassLoader.getSystemClassLoader() );
    }

    public Set<Class<?>> getClasses()
    {
        final Set<Class<?>> classes = new LinkedHashSet<>( resourceClasses );
        classes.addAll( providerClasses );
        return classes;
    }
}
