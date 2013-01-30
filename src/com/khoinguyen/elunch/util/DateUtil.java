package com.khoinguyen.elunch.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author <a href="mailto:ndkhoi168@gmail.com">Khoi NGUYEN</a>
 */
public class DateUtil {
    public static Date getSingaporeDate() {
        Date today = new Date();
        return getSingaporeDate(today);
    }
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
