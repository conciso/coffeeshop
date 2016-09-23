package com.github.conciso.coffeeshop;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.logging.LoggingFraction;

public class Main {

    public static void main(String...args) throws Exception {
        Swarm swarm = new Swarm(args);

        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class, "coffeeshop.war")
            .addPackages(true, Main.class.getPackage())
            .staticContent();

        swarm.
            fraction(LoggingFraction.createDefaultLoggingFraction())
            .start()
            .deploy(deployment);
    }
}
