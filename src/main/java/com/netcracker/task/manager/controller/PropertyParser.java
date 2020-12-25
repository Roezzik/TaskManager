package com.netcracker.task.manager.controller;

import com.netcracker.task.manager.view.utils.ViewConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyParser {

   public static String getPropertyValue(String PropertyName) throws PropertyReadException {

       Properties properties = new Properties();
       String propertyValue = "";
       String path = new File("staff/application.properties").getAbsolutePath();

       try (InputStream inputStream = new FileInputStream(path)) {
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
