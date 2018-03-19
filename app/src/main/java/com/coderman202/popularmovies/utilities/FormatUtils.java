package com.coderman202.popularmovies.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Reggie on 11/03/2018.
 * Custom utility class for formatting the numbers from 100000 to 100k, or 1200 to 1.2k, etc...
 * Also to format the release date
 */
public class FormatUtils {

    private static final long ONE_BILLION = 1000000000;
    private static final long ONE_MILLION = 1000000;
    private static final long ONE_THOUSAND = 1000;

    /**
     * Format the monetary amounts so they are easier on the eye.
     *
     * @param amount the amount
     * @return the string
     */
    public static String formatNumbers(int amount){
        double value;
        String numString = "$" + Integer.toString(amount);

        if((amount/ONE_BILLION) >= 1){
           value = Math.round(amount/ONE_BILLION);
            numString = "$" + Double.toString(value) + "BN";
        }else if(amount/ONE_MILLION >= 1){
            value = amount/ONE_MILLION;
            numString = "$" + Double.toString(value) + "M";
        }else if(amount/ONE_THOUSAND >= 1){
            value = amount/ONE_THOUSAND;
            numString = "$" + Double.toString(value) + "k";
        }
        return numString;
    }

    /**
     * Formats the date depending on the locale.
     * Such as Jan 20, 2009. or 20 Jan, 2009
     *
     * @param date   the date
     * @param locale the locale
     * @return the string
     */
    public static String formatDate(String date, Locale locale){
        DateFormat df = SimpleDateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try{
            d = sdf.parse(date);
        } catch (ParseException e){
            e.printStackTrace();
        }
        return df.format(d);
    }
}
