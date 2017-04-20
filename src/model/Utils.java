package model;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

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
        if (date.equals(""))
            return null;
        try {
            return new java.sql.Date(parser.parse(date).getTime());
        } catch (ParseException e) {
            if (date.matches("[0-9]+-[0-9]+-"))
                return new java.sql.Date(parser.parse(date + "01").getTime());
            if (date.matches("[0-9]+-[0-9]+"))
                return new java.sql.Date(parser.parse(date + "-01").getTime());
            if (date.matches("[0-9]+-"))
                return new java.sql.Date(parser.parse(date + "01-01").getTime());
            if (date.matches("[0-9]+"))
                return new java.sql.Date(parser.parse(date + "-01-01").getTime());
        }
        return new java.sql.Date(parser.parse(date).getTime());
    }

    static java.sql.Date parseDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static Double parseDouble(String number) throws NumberFormatException {
        if (number.equals(""))
            return null;
        return Double.parseDouble(number);
    }

    public static Integer parseInteger(String number) throws NumberFormatException {
        if (number.equals(""))
            return null;
        return Integer.parseInt(number);
    }

    public static java.sql.Date convertDate(LocalDate date) {
        if (date == null)
            throw new IllegalArgumentException();
        return Date.valueOf(date);
    }

    public static int countMatches(String string, String pattern) {
        int result = string.indexOf(pattern);
        if (result < 0)
            return 0;
        else
            return 1 + countMatches(string.substring(result + 1), pattern);
    }

    public static boolean containOnlyNumbers(String string) {
        return string.matches("[0-9]+");
    }

    public static boolean containOnlyLetters(String string) {
        return string.matches("[a-zA-Z]+");
    }

    public static String removeWhitespaces(String string) {
        return string.replaceAll("\\s+", "");
    }
}
