package com.edgomesdev.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity
public class Order extends PanacheMongoEntity {
    private ObjectId id;
    @BsonProperty("total_price")
    private Double totalPrice;
    private Status status;
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }
}
