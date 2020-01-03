package org.appointment.dataaccess.helpers;


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
    
    
    public static Properties loadProperties(String propertyName) {
    	
        try (InputStream stream = Configuration.class.getClassLoader().getResourceAsStream(propertyName)) {
        	
            Properties properties = new Properties();
            System.out.println(stream);
            properties.load(stream);
           
            return properties;
        } catch (IOException e) {
        	System.out.println(e.getMessage());
            return null;
        }
    }
}
