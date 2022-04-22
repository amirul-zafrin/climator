package org.az.climator.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.bson.types.ObjectId;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class DataEntity extends PanacheEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ue_id")
    public UserEntity userEntity;

    public LocalDateTime createdAt;

    public String filename;

    @Column(unique = true)
    public String objectId;

    public static void addData(UserEntity userEntity, String filename, String objectId) {
        DataEntity dataEntity = new DataEntity();

        dataEntity.userEntity = userEntity;
        dataEntity.createdAt = LocalDateTime.now();
        dataEntity.filename = filename;
        dataEntity.objectId = objectId;

        dataEntity.persist();
    }
// TODO: Don't let user save file with same name
    public static boolean searchByFilename(String filename){
        return find("filename",filename).firstResultOptional().isPresent();
    }

    public static List<DataEntity> searchByUserId(Long id){
        return list("ue_id", id);
    }

    public static void deleteByObjectId(String objectId) {
        delete("objectid", objectId);
    }
}
