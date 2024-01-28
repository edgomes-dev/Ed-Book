package com.edgomesdev.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity
public class Genre extends PanacheMongoEntity {
    private ObjectId id;
    private String name;
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }
}
