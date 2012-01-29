package com.onsale.deals.dao.impl;

import com.onsale.deals.bo.Sample;
import com.onsale.deals.dao.SampleDao;

public class SampleDaoImpl extends AbstractDao<Sample, Long> implements SampleDao {
    public SampleDaoImpl() {
        super(Sample.class);
    }
}
