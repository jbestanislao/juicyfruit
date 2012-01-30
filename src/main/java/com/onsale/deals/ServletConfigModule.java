package com.onsale.deals;

import com.google.inject.AbstractModule;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;
import com.onsale.deals.dao.SampleDao;
import com.onsale.deals.dao.impl.SampleDaoImpl;
import com.onsale.deals.services.SampleService;
import com.onsale.deals.services.impl.SampleServiceImpl;

public class ServletConfigModule extends ServletModule {
    @Override
    protected void configureServlets() {
        install(new JpaPersistModule("sampleJpaUnit"));
        filter("/*").through(PersistFilter.class);
    }
}
