package shared.utils;


import shared.constant.ViewConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Class for converting a date to a string and back
 */
public class DateConverter {
    
    /**
     * Date to string conversion function
     * @param date - date to convert to string
     * @return date as a string
     */
    public static String getStringDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(ViewConstants.DATE_FORMAT);
        return dateFormat.format(date);
    }
    
    /**
     * String to date function
     * @param stringDate - string with date
     * @return date
     */
    public static Date stringToDate(String stringDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(ViewConstants.DATE_FORMAT);
        return dateFormat.parse(stringDate);
    }
}
