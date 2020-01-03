package org.contract.web;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.xml.JacksonJaxbXMLProvider;
import org.contract.web.authorization.AuthorizationFilter;
import org.contract.web.authorization.CorsFilter;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class ContractServiceApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();

        resources.add(AuthorizationFilter.class);
        resources.add(CorsFilter.class);

        resources.add(JacksonJaxbJsonProvider.class);
        resources.add(JacksonJaxbXMLProvider.class);

        return resources;
    }
}