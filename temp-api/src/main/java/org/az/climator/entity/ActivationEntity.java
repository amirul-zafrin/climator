package org.az.climator.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "activation")
public class ActivationEntity extends PanacheEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    public UserEntity userEntity;

    public String token;
    public LocalDateTime createdAt;
    public LocalDateTime expiredAt;

    public static ActivationEntity setActivation(UserEntity userEntity) {
        ActivationEntity activationEntity = new ActivationEntity();

        activationEntity.userEntity = userEntity;
        activationEntity.token = UUID.randomUUID().toString();
        activationEntity.createdAt = LocalDateTime.now();
        activationEntity.expiredAt = LocalDateTime.now().plusMinutes(15);

        activationEntity.persist();
        return activationEntity;
    }

    public static String resetActivation(UserEntity userEntity) {
        ActivationEntity activationCode = userEntity.activationCode;
        activationCode.token = UUID.randomUUID().toString();
        return activationCode.token;
    }

    public static boolean checkExpiration(UserEntity userEntity) {
        ActivationEntity activationCode = userEntity.activationCode;
        return LocalDateTime.now().isBefore(activationCode.expiredAt);
    }

}
