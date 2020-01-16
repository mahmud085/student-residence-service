package org.appointment.web.helpers;

import org.appointment.web.Configuration;

import java.util.Properties;

public class ConfigHelper {
    private static final Properties PROPERTIES = Configuration.loadProperties();

    public static String getServiceUrl() {
        return PROPERTIES.getProperty("serviceUrl");
    }

    public static String getAuthServiceUrl() {
        return PROPERTIES.getProperty("authServiceUrl");
    }

    public static String getContractServiceUrl() {
        return PROPERTIES.getProperty("contractServiceUrl");
    }
}
