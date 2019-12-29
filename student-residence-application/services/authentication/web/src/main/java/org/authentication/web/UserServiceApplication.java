package org.authentication.web;

import com.fasterxml.jackson.jaxrs.xml.JacksonJaxbXMLProvider;
import org.authentication.web.authorization.AuthorizationFilter;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class UserServiceApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();

        resources.add(AuthorizationFilter.class);
        resources.add(JacksonJaxbXMLProvider.class);

        return resources;
    }
}