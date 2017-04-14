package model;

import java.sql.Date;
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

    static java.sql.Date parseDate(String date) throws ParseException {
        return (Date) parser.parse(date);
    }
}
