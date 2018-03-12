package com.coderman202.popularmovies.utilities;

/**
 * Created by Reggie on 11/03/2018.
 * Custom utility class for formatting the numbers from 100000 to 100k, or 1200 to 1.2k, etc...
 */

public class FormatUtils {

    private static final long ONE_BILLION = 1000000000;
    private static final long ONE_MILLION = 1000000;
    private static final long ONE_THOUSAND = 1000;

    public static String formatNumbers(int amount){
        double value = 0;
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
}
