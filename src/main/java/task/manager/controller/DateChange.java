package task.manager.controller;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateChange {
    
    public static String getStringDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        return dateFormat.format(date);
    }
    
    public static Date stringToDate(String stringDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        return dateFormat.parse(stringDate);
    }
}
