package com.onsale.deals.services;

import com.onsale.deals.bo.Sample;

public interface SampleService {
    Sample findById(Long id);
    void remove(Long id);
}
