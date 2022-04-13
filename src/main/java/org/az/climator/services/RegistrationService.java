package org.az.climator.services;

import org.az.climator.dto.RegistrationDTO;
import org.az.climator.entity.ActivationEntity;
import org.az.climator.entity.UserEntity;
import org.az.climator.validation.EmailValidation;

public class RegistrationService {

    private static String message;

    public static boolean validation(RegistrationDTO info) {
        if(!EmailValidation.validate(info.getEmail())) {
            message = "Email is not valid!";
            return false;
        }else if (UserEntity.existEmail(info.getEmail())) {
            message = "Email is already registered!";
            return false;
        } else if (UserEntity.existUsername(info.getUsername())) {
            message = "Username already used!";
            return false;
        } else {
            UserEntity.addUser(info.getEmail(), info.getUsername(), info.getPassword());
            message = info.getUsername();
            return true;
        }
    }

    public static boolean userActivation(Long id, String token) {
        UserEntity user = UserEntity.findById(id);
        boolean activation = user.activationCode.token.equals(token);
        boolean expired = ActivationEntity.checkExpiration(user);

        if(activation && expired) {
            user.activated = true;
            return true;
        }
        else {
            message = "Token is invalid or expired! Please request new token";
            return false;
        }
    }

    public static void resetActivation(Long id) {
        UserEntity user = UserEntity.findById(id);
        ActivationEntity.resetActivation(user);
    }

    public static String getMessage(){
        return message;
    }

}
