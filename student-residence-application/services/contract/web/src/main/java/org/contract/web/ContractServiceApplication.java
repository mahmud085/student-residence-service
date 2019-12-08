package org.contract.web;

import com.fasterxml.jackson.jaxrs.xml.JacksonJaxbXMLProvider;
import org.contract.web.filters.AuthorizationFilter;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class ContractServiceApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();

        resources.add(AuthorizationFilter.class);
        resources.add(JacksonJaxbXMLProvider.class);

        return resources;
    }
}