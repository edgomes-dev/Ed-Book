package com.edgomesdev.service;

import com.edgomesdev.exception.NotFoundException;
import com.edgomesdev.model.Genre;
import com.edgomesdev.repository.GenreRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class GenreService {
    @Inject
    GenreRepository repository;

    public List<Genre> findAll() {
        return repository.listAll();
    };

    public Genre findById(String id) {
        ObjectId genreId = new ObjectId(id);
        Optional<Genre> genre = repository.findByIdOptional(genreId);

        return genre.orElseThrow(() -> new NotFoundException("ID inválido"));
    };

    public Genre create(Genre genre) {
        genre.persist();

        return genre;
    };

    public Genre update(String id, Genre genre) {
        Genre oldGenre = this.findById(id);

        oldGenre.setName(genre.getName());
        oldGenre.update();

        return  oldGenre;
    };
}
