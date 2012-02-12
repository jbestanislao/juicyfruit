/*
 * Copyright 2011 OSRP, LLC.
 */
package com.onsale.deals;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import javax.servlet.ServletContextEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Guicifier extends GuiceServletContextListener {

    private static final Logger log = LoggerFactory.getLogger(Guicifier.class);
    private static Injector injector;

    @Override
    protected Injector getInjector() {
        log.debug("Initializing injector");
        synchronized (Guicifier.class) {
            if (injector != null) {
                throw new IllegalStateException("Injector already exists");
            }

            injector = Guice.createInjector(new ServletConfigModule(), new ServiceModule());
        }

        return injector;
    }

    public static Injector injector() throws IllegalStateException {
        if (injector == null) {
            throw new IllegalStateException("Guicifier not yet initialized");
        }

        return injector;
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.debug("Removing injector");
        super.contextDestroyed(servletContextEvent);
        synchronized (Guicifier.class) {
            if (injector == null) {
                log.warn("There was no injector to remove");
            }

            injector = null;
        }
    }
}
