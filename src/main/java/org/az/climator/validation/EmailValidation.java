package org.az.climator.validation;

import org.az.climator.entity.UserEntity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidation {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate (String email, String username) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        boolean format = matcher.find();

        boolean emailExist = UserEntity.existEmail(email);
        boolean usernameExist = UserEntity.existUsername(username);

        return (format || emailExist) || usernameExist;
    }

}
