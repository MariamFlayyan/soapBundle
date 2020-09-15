///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// File:
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Author: Mariam flayyan
//
// Nokia - Confidential
// Do not use, distribute, or copy without consent of Nokia.
// Copyright (c) 2020 Nokia. All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package com.techprimers.springbootsoapexample;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.osgi.io.OsgiBundleResourcePatternResolver;


@Configuration
@SpringBootApplication(scanBasePackages = "com.techprimers.springbootsoapexample")
@PropertySource("classpath:application.properties")
public class SpringBootBundleActivator extends SpringBootServletInitializer
        implements BundleActivator
{

    ConfigurableApplicationContext appContext;

    /**
     * This Method is Used to start the osgi bundle
     *
     * @param bundleContext osgi bundle context
     */
    @Override
    public void start(BundleContext bundleContext)
    {
        // Set context classloader
        Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
        // trick to enable scan: get osgi resource pattern resolver
        OsgiBundleResourcePatternResolver resourceResolver = new OsgiBundleResourcePatternResolver(bundleContext.getBundle());
        // and provide it to spring application
        appContext = new SpringApplication(resourceResolver, SpringBootBundleActivator.class).run();
    }

    /**
     * This Method is Used to stop osgi bundle
     *
     * @param bundleContext osgi bundle context
     */
    @Override
    public void stop(BundleContext bundleContext) {
        SpringApplication.exit(appContext, () -> 0);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBundleActivator.class);
    }

    /**
     * This Method is Used to call application.sources() to add sources
     *
     * @return application.sources(DemoApplication.class)
     */
    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder application) {
        return application.sources(SpringBootBundleActivator.class);
    }

}

