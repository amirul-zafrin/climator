package org.az.climator.services;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import org.az.climator.entity.UserEntity;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.CookieParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

//TODO: Implement refresh token

@Singleton
public class JWTService {
    @Inject
    JWTParser jwtParser;

    @ConfigProperty(name = "org.az.climator.secret") String secret;

    public String generateJWT(String username) {
        UserEntity user = UserEntity.searchByUsername(username);
        return Jwt.issuer("climator")
                .subject(user.username)
                .claim("id",user.id)
                .groups(user.role)
                .expiresAt(System.currentTimeMillis() + 3600)
                .issuedAt(System.currentTimeMillis())
                .signWithSecret(secret);
    }

    public boolean verifyJWT(Long id, @Context SecurityContext securityContext) {
         UserEntity user = UserEntity.findById(id);
         return user.username.equals(securityContext.getUserPrincipal().getName());
    }

    public boolean verifyJWTCookies(Long id, @CookieParam("jwt") String jwtCookie) throws ParseException {
        UserEntity user = UserEntity.findById(id);
        JsonWebToken jwt = jwtParser.verify(jwtCookie, secret);
        return jwt.getName().equals(user.username);
    }

}
