package com.netcracker.task.manager.controller;


import com.netcracker.task.manager.view.utils.ViewConstants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertyParser {

    private String fileFormat;

    private String pathToBackup;

///
    public String getFileFormat() throws PropertyReadException {

        if (fileFormat == null) {
            fileFormat = getPropertyValue("backup_format");
        }

        return fileFormat;
    }

    public String getPathToBackup() throws PropertyReadException {
        if (pathToBackup == null) {
            if (fileFormat == null) {
                getFileFormat();
            }
            if (fileFormat.equals("txt"))
                pathToBackup = getPropertyValue("path_to_bin_backup");
            if (fileFormat.equals("bin"))
                pathToBackup = getPropertyValue("path_to_txt_backup");
        }
        return pathToBackup;
    }
///

    public static final String PATH_TO_PROPERTIES = "staff/application.properties";

    public static String getPropertyValue(String propertyName) throws PropertyReadException {

        Properties properties = new Properties();
        String propertyValue;

        try (InputStream inputStream = new FileInputStream(PATH_TO_PROPERTIES)) {
            properties.load(inputStream);
            propertyValue = properties.getProperty(propertyName);
        } catch (IOException e) {
            throw new PropertyReadException(ViewConstants.ERROR_READ_PROPERTY);
        }
        return propertyValue;
    }

    public static int getIntegerPropertyValue(String PropertyName) throws NumberFormatException, PropertyReadException {
        return Integer.parseInt(getPropertyValue(PropertyName));
    }
}
