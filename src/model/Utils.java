package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Matus Cuper on 14.4.2017.
 *
 * Class represents utility functions
 */
public class Utils {

    private static SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");


    private Utils() {}

    @SuppressWarnings("deprecation")
    public static java.sql.Date parseDate(String date) throws ParseException {
        return new java.sql.Date(parser.parse(date).getTime());
    }
}
