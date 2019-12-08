package org.contract.web;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.contract.web.resources.implementations.ContractResourceImpl;
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

        ResourceConfig config = ResourceConfig.forApplicationClass(ContractServiceApplication.class);
        config.register(injector.getInstance(ContractResourceImpl.class));

        JdkHttpServerFactory.createHttpServer(baseUri, config);
    }
}
