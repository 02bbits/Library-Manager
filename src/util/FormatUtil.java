package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class FormatUtil {
    public static boolean isValidDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        // Define a regex pattern for email validation
        String emailRegex = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    public static boolean isValidGenresField(String genresField) {
        if (genresField == null || genresField.isEmpty()) {
            return false;
        }

        String pattern = "^([^,]+)(,\\s*[^,]+)*$";
        return Pattern.compile(pattern).matcher(genresField).matches();
    }

    public static ArrayList<String> genresStringToList(String genresString) {
        if (genresString == null || genresString.isEmpty()) {
            return null;
        }

        String[] genresArray = genresString.split("\\s*,\\s*");
        return new ArrayList<>(Arrays.asList(genresArray));
    }
}
