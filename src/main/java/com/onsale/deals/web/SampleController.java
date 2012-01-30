package com.onsale.deals.web;

import com.google.inject.Injector;
import com.onsale.deals.Guicifier;
import com.onsale.deals.bo.Sample;
import com.onsale.deals.services.SampleService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SampleController extends HttpServlet {
    @Inject
    private SampleService sampleService;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Injector injector = Guicifier.injector();
        injector.injectMembers(this);
        System.out.println("Test Sample Controller");
        Sample sample = sampleService.findById(1L);
        System.out.println(sample.getEmail());
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Test Sample Controller");
    }
}
