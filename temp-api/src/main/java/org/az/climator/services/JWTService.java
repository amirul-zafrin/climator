package org.az.climator.services;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import org.az.climator.entity.UserEntity;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

//TODO: Implement refresh token

@Singleton
public class JWTService {

    public String generateJWT(String username) {
        UserEntity user = UserEntity.searchByUsername(username);

        String sign = Jwt.issuer("climator")
                .subject(user.username)
                .claim("id", user.id)
                .groups(user.role)
                .expiresAt(System.currentTimeMillis() + 3600)
                .issuedAt(System.currentTimeMillis())
                .sign();

        return "Bearer " + sign;
    }

    public boolean verifyJWT(Long id, @Context SecurityContext securityContext) {
        UserEntity user = UserEntity.findById(id);
        return user.username.equals(securityContext.getUserPrincipal().getName());
    }

}
