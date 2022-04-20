package org.az.climator.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.bson.types.ObjectId;
import javax.persistence.*;
import java.time.LocalDateTime;

//    TODO: Store uploaded data

@Entity
public class DataEntity extends PanacheEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ue_id")
    public UserEntity userEntity;

    public LocalDateTime createdAt;

    public String filename;

    public ObjectId objectId;

    public static void addData(UserEntity userEntity, String filename, ObjectId objectId) {
        DataEntity dataEntity = new DataEntity();

        dataEntity.userEntity = userEntity;
        dataEntity.createdAt = LocalDateTime.now();
        dataEntity.filename = filename;
        dataEntity.objectId = objectId;

        dataEntity.persist();
    }

}
