package com.netcracker.task.manager.controller;


import com.netcracker.task.manager.view.utils.AlertForm;
import com.netcracker.task.manager.view.utils.ViewConstants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * class for working with Property file
 */
public class PropertyParser {
    
    /**
     * PATH_TO_PROPERTIES - path to the property file
     */
    public final   String         PATH_TO_PROPERTIES = "staff/application.properties";
    private static PropertyParser instance;
    private final  Properties     properties;
    
    private PropertyParser() {
        properties = new Properties();
        try (InputStream inputStream = new FileInputStream(PATH_TO_PROPERTIES)) {
            properties.load(inputStream);
        } catch (IOException e) {
            AlertForm.errorAlert(ViewConstants.ERROR_READ_PROPERTY);
            System.exit(4);
        }
    }
    
    public static PropertyParser getInstance() {
        if (instance == null) {
            instance = new PropertyParser();
        }
        return instance;
    }
    
    /**
     * getPropertyValue is to return the value of the variable by its name from the property file
     *
     * @param propertyName - the name of the variable
     * @return the value of the variable by its name from the property file
     */
    public String getPropertyValue(String propertyName) {
        return properties.getProperty(propertyName);
    }
    
}
