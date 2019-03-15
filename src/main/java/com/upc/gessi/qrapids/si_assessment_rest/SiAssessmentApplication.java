package com.upc.gessi.qrapids.si_assessment_rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SiAssessmentApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SiAssessmentApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SiAssessmentApplication.class, args);
    }

}

