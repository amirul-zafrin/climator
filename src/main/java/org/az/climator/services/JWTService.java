package org.az.climator.services;

import io.smallrye.jwt.build.Jwt;
import org.az.climator.entity.UserEntity;

import javax.inject.Singleton;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

@Singleton
public class JWTService {

    public String generateJWT(UserEntity user) {
        return Jwt.issuer("climator")
                .subject(user.username)
                .claim("id",user.id)
                .groups(user.role)
                .expiresAt(System.currentTimeMillis() + 3600)
                .issuedAt(System.currentTimeMillis())
                .sign();
    }

    public boolean verifyJWT(Long id, @Context SecurityContext securityContext) {
         UserEntity user = UserEntity.findById(id);
         return user.username.equals(securityContext.getUserPrincipal().getName());
    }

}
