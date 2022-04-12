package org.az.climator.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

//    TODO: Store uploaded data

@Entity
@TypeDefs({@TypeDef(name = "json", typeClass = JsonStringType.class)})
public class DataEntity extends PanacheEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    public UserEntity userEntity;

    @Type(type = "json")
    public Map<String, Object> uploadedData = new HashMap<>();

    public LocalDateTime createdAt;

}
