package com.github.ligangty.undertow.resteasy.test;

import com.github.ligangty.undertow.resteasy.test.instance.Deployer;
import io.undertow.Undertow;
import io.undertow.predicate.Predicate;
import io.undertow.predicate.Predicates;
import io.undertow.server.handlers.encoding.ContentEncodingRepository;
import io.undertow.server.handlers.encoding.EncodingHandler;
import io.undertow.server.handlers.encoding.GzipEncodingProvider;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import java.util.Collection;

@ApplicationScoped
public class TestDeployer
        implements Deployer
{

    private final Logger logger = LoggerFactory.getLogger( getClass() );

    private Undertow server;

    public TestDeployer()
    {
    }

    @Inject
    private Deployment deployment;

    @Override
    public void stop()
    {
        if ( server != null )
        {
            server.stop();
        }
    }

    @Override
    public void deploy()
            throws Exception
    {
        final DeploymentInfo di = deployment.getDeployment( "" ).setContextPath( "/" );

        final DeploymentManager dm = Servlets.defaultContainer().addDeployment( di );

        Collection<String> list = Servlets.defaultContainer().listDeployments();
        logger.info( "List deployments: {}", list );

        dm.deploy();

        try
        {
            final Integer port = 8080;
            final String bindAddr = "0.0.0.0";
            logger.info( "Start Undertow server, bind: {}, port: {}", bindAddr, port );
            server = buildAndStartUndertow( dm, port, bindAddr );
            logger.info( "App listening on {}:{}\n\n", bindAddr, port );
        }
        catch ( Exception e )
        {
            logger.error( "Deploy failed", e );
            throw new Exception( "Deploy failed", e );
        }
    }

    private Undertow buildAndStartUndertow( DeploymentManager dm, Integer port, String bind )
            throws Exception
    {
        Undertow undertow =
                Undertow.builder().setHandler( getGzipEncodeHandler( dm ) ).addHttpListener( port, bind ).build();
        undertow.start();
        return undertow;
    }

    private EncodingHandler getGzipEncodeHandler( final DeploymentManager dm )
            throws ServletException
    {
        // FROM: https://stackoverflow.com/questions/28295752/compressing-undertow-server-responses#28329810
        final Predicate sizePredicate = Predicates.parse( "max-content-size[" + Long.toString( 5 * 1024 ) + "]" );

        return new EncodingHandler(
                new ContentEncodingRepository().addEncodingHandler( "gzip", new GzipEncodingProvider(), 50,
                                                                    sizePredicate ) ).setNext( dm.start() );
    }

}
