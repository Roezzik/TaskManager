package com.netcracker.task.manager.controller;


import com.netcracker.task.manager.view.utils.ViewConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateConverter {
    
    public static String getStringDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(ViewConstants.DATE_FORMAT);
        return dateFormat.format(date);
    }
    
    public static Date stringToDate(String stringDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(ViewConstants.DATE_FORMAT);
        return dateFormat.parse(stringDate);
    }
}
