package com.edgomesdev.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity
public class Product extends PanacheMongoEntity {
    private ObjectId id;
    private String title;
    private String publisher;
    @BsonProperty("release_year")
    private String releaseYear;
    private String description;
    private Integer quantity;
}
