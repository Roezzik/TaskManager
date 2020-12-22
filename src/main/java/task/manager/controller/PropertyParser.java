package task.manager.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyParser {

    public static String getPropertyValue(String PropertyName) {
        Properties properties = new Properties();
        String propertyValue = "";

        // todo find out reasons work with file like with resource and provide me details on syncup
        try (InputStream inputStream = PropertyParser.class.getClassLoader().getResourceAsStream("staff/application.properties")) {
            properties.load(inputStream);
            propertyValue = properties.getProperty(PropertyName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return propertyValue;
    }

    public static int getIntegerPropertyValue(String PropertyName) throws NumberFormatException {
        return Integer.parseInt(getPropertyValue(PropertyName));
    }
}
