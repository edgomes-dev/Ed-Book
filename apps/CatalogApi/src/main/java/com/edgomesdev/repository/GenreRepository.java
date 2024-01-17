package com.edgomesdev.repository;

import com.edgomesdev.model.Genre;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GenreRepository implements PanacheMongoRepository<Genre> {
}
