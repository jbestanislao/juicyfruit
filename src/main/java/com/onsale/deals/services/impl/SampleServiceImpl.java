package com.onsale.deals.services.impl;

import com.google.inject.Inject;
import com.onsale.deals.bo.Sample;
import com.onsale.deals.dao.SampleDao;
import com.onsale.deals.services.SampleService;

public class SampleServiceImpl implements SampleService {
    private SampleDao sampleDao;

    @Inject
    public SampleServiceImpl(SampleDao sampleDao) {
        this.sampleDao = sampleDao;
    }

    public Sample findById(Long id) {
        return sampleDao.findById(id);
    }
}
