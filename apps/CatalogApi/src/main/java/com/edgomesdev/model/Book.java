package com.edgomesdev.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity
public class Book extends PanacheMongoEntity {
    private String title;
    private String publisher;
    @BsonProperty("release_year")
    private String releaseYear;
    private String description;
    private Integer stock;
}
