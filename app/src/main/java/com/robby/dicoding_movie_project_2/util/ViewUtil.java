package com.robby.dicoding_movie_project_2.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Robby Tan
 */

public class ViewUtil {

    public static String getEasyReadableDate(String dateInString, String inputPattern, String outputPattern) {
        if (dateInString != null && inputPattern != null && outputPattern != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(inputPattern);
                Date date = sdf.parse(dateInString);
                sdf.applyPattern(outputPattern);
                return sdf.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}
