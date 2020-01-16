package org.appointment.web;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.xml.JacksonJaxbXMLProvider;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class AppointmentServiceApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();

        resources.add(JacksonJaxbXMLProvider.class);
        resources.add(JacksonJaxbJsonProvider.class);

        return resources;
    }
}