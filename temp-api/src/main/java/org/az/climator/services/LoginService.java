package org.az.climator.services;

import io.quarkus.elytron.security.common.BcryptUtil;
import org.az.climator.dto.LoginDTO;
import org.az.climator.entity.UserEntity;

public class LoginService {

    public static String verify(LoginDTO info) {
        String message = null;
        if (!UserEntity.existUsername(info.getUsername())) {
            message = String.format("User: %s is not exist!",info.getUsername());
            return message;
        }

        UserEntity user = UserEntity.searchByUsername(info.getUsername());
        boolean checkPassword = BcryptUtil.matches(info.getPassword(), user.encodedPassword);

        if(!user.activated) {
            message = "Please activate your account before login!";
            return message;
        }
        else if(!checkPassword) {
            message = "Wrong password";
            return message;
        }
        return message;
    }

}
