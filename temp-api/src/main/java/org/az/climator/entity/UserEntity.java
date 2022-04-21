package org.az.climator.entity;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import org.az.climator.dto.RegistrationDTO;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

//TODO: Consider change from Active record pattern to Repository pattern

@Entity
@UserDefinition
public class UserEntity extends PanacheEntity {

    @Roles
    public String role;

    @Email
    @NotBlank
    @Column(unique = true)
    public String email;

    @Username
    @NotBlank
    @Column(unique = true)
    public String username;

    @Password
    public String encodedPassword;

    public Boolean activated;

    public LocalDateTime createdAt;

//    @OneToOne(fetch = FetchType.LAZY)
    @OneToOne(cascade=CascadeType.ALL, mappedBy = "userEntity")
    public ActivationEntity activationCode;

    @OneToMany( mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<DataEntity> data;

    public static UserEntity addUser(RegistrationDTO info){
        UserEntity user = new UserEntity();
        user.email = info.getEmail();
        user.username = info.getUsername();
        user.encodedPassword = BcryptUtil.bcryptHash(info.getPassword());
        user.activationCode = ActivationEntity.setActivation(user);

        user.role = "user";
        user.activated = false;
        user.createdAt = LocalDateTime.now();

        user.persist();
        return user;
    }

    //TODO: case-insensitive query
    public static boolean existUsername(String username) {
        Optional<PanacheEntityBase> user = find("username", username).firstResultOptional();
        return user.isPresent();
    }

    public static boolean existEmail(String email){
        Optional<PanacheEntityBase> userEmail = find("email", email).firstResultOptional();
        return userEmail.isPresent();
    }

    public static UserEntity searchByUsername(String username){
        return find("username",username).firstResult();
    }

}
