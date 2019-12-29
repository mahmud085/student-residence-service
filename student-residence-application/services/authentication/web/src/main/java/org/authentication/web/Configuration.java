package org.authentication.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    public static Properties loadProperties() {
        try (InputStream stream = Configuration.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties properties = new Properties();
            properties.load(stream);
            return properties;
        } catch (IOException e) {
            return null;
        }
    }
}
