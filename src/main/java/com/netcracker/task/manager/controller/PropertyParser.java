package com.netcracker.task.manager.controller;


import com.netcracker.task.manager.view.utils.ViewConstants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertyParser {
    
    public static final String PATH_TO_PROPERTIES = "staff/application.properties";
    
    public static String getPropertyValue(String PropertyName) throws PropertyReadException {
        
        Properties properties = new Properties();
        String     propertyValue;
        
        try (InputStream inputStream = new FileInputStream(PATH_TO_PROPERTIES)) {
            properties.load(inputStream);
            propertyValue = properties.getProperty(PropertyName);
        } catch (IOException e) {
            throw new PropertyReadException(ViewConstants.ERROR_READ_PROPERTY);
        }
        return propertyValue;
    }
    
    public static int getIntegerPropertyValue(String PropertyName) throws NumberFormatException, PropertyReadException {
        return Integer.parseInt(getPropertyValue(PropertyName));
    }
}
