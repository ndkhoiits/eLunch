package com.khoinguyen.elunch.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created with IntelliJ IDEA.
 * User: khoinguyen
 * Date: 1/26/13
 * Time: 9:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class DateUtil {
    public static Date getSingaporeDate(Date date) {
        Date result;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String strDate = sdf.format(date);
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        try {
            return sdf1.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
