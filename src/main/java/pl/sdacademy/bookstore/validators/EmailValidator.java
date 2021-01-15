package pl.sdacademy.bookstore.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
    private static final String MAIL_PATTERN_STRING = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    private static final Pattern MAIL_PATTERN = java.util.regex.Pattern.compile(MAIL_PATTERN_STRING);

    public boolean isValidEmailAddress(String email) {
        Matcher matcher = MAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}
