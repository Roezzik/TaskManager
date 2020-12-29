package com.netcracker.task.manager.controller;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertyParser {
    
    public final   String         PATH_TO_PROPERTIES = "staff/application.properties";
    private static PropertyParser instance;
    private final  Properties     properties;
    
    private PropertyParser() {
        properties = new Properties();
        try (InputStream inputStream = new FileInputStream(PATH_TO_PROPERTIES)) {
            properties.load(inputStream);
        } catch (IOException e) {
        }
    }
    
    public static PropertyParser getInstance() {
        if (instance == null) {
            instance = new PropertyParser();
        }
        return instance;
    }
    
    public String getPropertyValue(String propertyName) {
        return properties.getProperty(propertyName);
    }
    
}
