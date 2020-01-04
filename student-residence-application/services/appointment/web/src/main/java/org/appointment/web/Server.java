package org.appointment.web;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.appointment.web.authorization.AuthorizationFilter;
import org.appointment.web.authorization.CorsFilter;
import org.appointment.web.resources.implementations.AppointmentResourceImpl;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Properties;

public class Server {
    private static Properties properties = Configuration.loadProperties();

    public void start() {
        String serviceUrl = properties.getProperty("serviceUrl");
        URI baseUri = UriBuilder.fromUri(serviceUrl).build();
        Injector injector = Guice.createInjector(new IoCModule());

        ResourceConfig config = ResourceConfig.forApplicationClass(AppointmentServiceApplication.class);
        config.register(injector.getInstance(AppointmentResourceImpl.class));

        config.register(injector.getInstance(AuthorizationFilter.class));
        config.register(injector.getInstance(CorsFilter.class));

        JdkHttpServerFactory.createHttpServer(baseUri, config);
        System.out.println("Server ready to serve your requests...");
    }
}
