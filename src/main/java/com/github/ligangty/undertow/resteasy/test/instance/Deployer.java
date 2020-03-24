package com.github.ligangty.undertow.resteasy.test.instance;


public interface Deployer
{
    void deploy()
            throws Exception;

    void stop();
}
