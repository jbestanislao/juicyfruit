package com.onsale.deals;

import com.google.inject.AbstractModule;
import com.onsale.deals.services.SampleService;
import com.onsale.deals.services.impl.SampleServiceImpl;

public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(SampleService.class).to(SampleServiceImpl.class);
    }
}
