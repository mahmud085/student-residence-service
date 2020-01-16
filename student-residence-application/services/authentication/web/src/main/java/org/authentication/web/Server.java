package org.authentication.web;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.authentication.web.authorization.AuthorizationFilter;
import org.authentication.web.authorization.CorsFilter;
import org.authentication.web.resources.implementations.AuthenticationResourceImpl;
import org.authentication.web.resources.implementations.UserResourceImpl;
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

        ResourceConfig config = ResourceConfig.forApplicationClass(UserServiceApplication.class);
        config.register(injector.getInstance(UserResourceImpl.class));
        config.register(injector.getInstance(AuthenticationResourceImpl.class));

        config.register(injector.getInstance(AuthorizationFilter.class));
        config.register(injector.getInstance(CorsFilter.class));

        JdkHttpServerFactory.createHttpServer(baseUri, config);
        System.out.println("Server ready to serve your requests...");
    }
}
