package task.manager.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyParser {

    public static String getPropertyValue(String PropertyName) {
        Properties properties = new Properties();
        String propertyValue = "";

        try (InputStream inputStream = PropertyParser.class.getClassLoader().getResourceAsStream("application.properties")) {
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

    //test main
    /*public static void main(String[] args) {
        Integer x = Setting.getIntegerPropertyValue("x");
        System.out.println(x);
    }*/
}
