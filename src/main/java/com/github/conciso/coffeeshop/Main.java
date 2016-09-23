package com.github.conciso.coffeeshop;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

public class Main {

    public static void main(String...args) throws Exception {
        Swarm swarm = new Swarm(args);

        JAXRSArchive archive = ShrinkWrap.create(JAXRSArchive.class);
        archive.addPackage( Main.class.getPackage() );
        archive.addAllDependencies();

        swarm.start().deploy( archive );
    }
}
