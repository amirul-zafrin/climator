package org.az.climator.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private String jwt;
    private String username;
}
