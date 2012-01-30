/*
 * Copyright 2011 OSRP, LLC.
 */
package com.onsale.deals;

import java.io.InputStream;
import java.net.URL;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;

import javax.servlet.ServletContextEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a ServletContextListener to manage the lifecycle of the Guice
 * Injector along with the application. It also provides global access to the
 * application Injector for components that do not participate directly under
 * the dependency injection tree.
 * 
 * <p>For example, ToolTwist components are instantiated by ToolTwist directly
 * so their dependencies cannot be automatically injected by Guice. In that case
 * they can request on-demand injection as part of their initialization
 * procedure:
 * 
 * <pre>
 *     public class MyToolTwistHelper extends WbdProductionHelper {
 * 
 *         @Override
 *         public XData preFetch(UimData ud) throws Exception {
 *             Guicifier.injector().injectMembers(this);
 *             // rest of init...
 *         }
 *     }
 * </pre>
 *
 * @author Edward Samson <Edward.Samson@pcmall.com>
 */
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

            //injector = Guice.createInjector(new ServletConfig());
            injector = Guice.createInjector(new JpaPersistModule("sampleJpaUnit"), new ServiceModule());
        }

        return injector;
    }

    /**
     * Access to the application-wide injector.
     * @return
     * @throws IllegalStateException if called before the Injector has been
     *      initialized
     */
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
