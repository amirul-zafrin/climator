package org.az.climator.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.bson.types.ObjectId;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//    TODO: Store uploaded data

@Entity
public class DataEntity extends PanacheEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ue_id")
    public UserEntity userEntity;

    public LocalDateTime createdAt;

    public String filename;

    public String objectId;

    public static void addData(UserEntity userEntity, String filename, String objectId) {
        DataEntity dataEntity = new DataEntity();

        dataEntity.userEntity = userEntity;
        dataEntity.createdAt = LocalDateTime.now();
        dataEntity.filename = filename;
        dataEntity.objectId = objectId;

        dataEntity.persist();
    }

    public static DataEntity searchByFilename(String filename){
        return find("filename",filename).firstResult();
    }

    public static List<DataEntity> searchByUserId(Long id){
        return list("ue_id", id);
    }

}
