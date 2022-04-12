package org.az.climator.services;

import io.smallrye.jwt.build.Jwt;
import org.az.climator.entity.UserEntity;

import javax.inject.Singleton;

@Singleton
public class JWTService {

    public String generateJWT(UserEntity user) {
        return Jwt.issuer("climator")
                .subject(user.id.toString())
                .claim("username", user.username)
                .claim("email",user.email)
                .groups(user.role)
                .expiresAt(System.currentTimeMillis() + 900000)
                .issuedAt(System.currentTimeMillis())
                .sign();
    }


}
