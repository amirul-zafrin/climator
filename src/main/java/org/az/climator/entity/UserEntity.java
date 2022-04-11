package org.az.climator.entity;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "user")
@UserDefinition
public class UserEntity extends PanacheEntity {

    @Roles
    public String role;

    @Email
    @NotBlank
    public String email;

    @Username
    @NotBlank
    public String username;

    @Password
    public String encodedPassword;

    public Boolean activated;

    public LocalDateTime createdAt;

    @OneToOne
    public ActivationEntity activationCode;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    public Set<DataEntity> data;

    public static UserEntity addUser(String email, String username, String password){
        UserEntity user = new UserEntity();
        user.email = email;
        user.username = username;
        user.encodedPassword = BcryptUtil.bcryptHash(password);

        user.role = "USER";
        user.activated = false;
        user.createdAt = LocalDateTime.now();

        user.persist();
        return user;
    }

    public static UserEntity searchByUsername(String username){
        return find("username",username).firstResult();
    }

    public static boolean existedUsername(String username) {
        return true;
    }
}
