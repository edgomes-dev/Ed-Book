package com.edgomesdev.repository;

import com.edgomesdev.model.Product;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductRepository implements PanacheMongoRepository<Product> {
}
