package org.az.climator.dto;

import lombok.Data;
import org.az.climator.validation.PasswordValidation.ValidPassword;

@Data
public class RegistrationDTO {
    private String email;
    private String username;

    @ValidPassword
    private String password;

    private String confirmedPassword;

}
