package com.github.ligangty.undertow.resteasy.test;

import com.github.ligangty.undertow.resteasy.test.instance.Deployer;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JAXRSBooter
{
    public static void main( final String[] args )
    {
        try
        {
            JAXRSBooter booter = new JAXRSBooter();
            booter.run();
        }
        catch ( final Exception e )
        {
            e.printStackTrace();
            System.err.printf( "ERROR: %s", e.getMessage() );
            System.exit( 0 );
        }
    }

    private final Logger logger = LoggerFactory.getLogger( getClass() );

    protected Weld weld;

    protected WeldContainer container;

    protected Deployer deployer;

    public void run()
            throws Exception
    {
        init();
        deploy();

        logger.info( "Start waiting on {}", this );
        synchronized ( this )
        {
            try
            {
                wait();
            }
            catch ( final InterruptedException e )
            {
                e.printStackTrace();
                logger.info( "App exiting" );
            }
        }
    }

    private void init()
    {
        weld = new Weld();
//        weld.property( "org.jboss.weld.se.archive.isolation", false );

        // Weld shutdown hook might disturb application shutdown hooks. We need to disable it.
        weld.skipShutdownHook();

        container = weld.initialize();
        //        final BeanManager bmgr = container.getBeanManager();
        //        logger.info( "\n\n\nStarted BeanManager: {}\n\n\n", bmgr );
    }

    public void deploy()
            throws Exception
    {
        TestDeployer deployer = container.select( TestDeployer.class ).get();
        if ( deployer == null )
        {
            logger.warn( "No deployer found!" );
            return;
        }
        logger.info( "Deployer: {}", deployer.getClass() );
        deployer.deploy();
    }
}
