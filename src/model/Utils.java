package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

/**
 * Created by Matus Cuper on 14.4.2017.
 *
 * Class represents utility functions
 */
class Utils {

    private static final Logger LOG = Logger.getLogger(Utils.class.getName());

    private static SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");


    private Utils() {}

    @SuppressWarnings("deprecation")
    static java.sql.Date parseDate(String date) throws ParseException {
        return new java.sql.Date(parser.parse(date).getYear(), parser.parse(date).getMonth(), parser.parse(date).getDate());
    }
}
