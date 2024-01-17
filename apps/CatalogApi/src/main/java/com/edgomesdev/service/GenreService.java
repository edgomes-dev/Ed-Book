package com.edgomesdev.service;

import com.edgomesdev.model.Genre;
import com.edgomesdev.repository.GenreRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
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

        return genre.orElseThrow(NotFoundException::new);
    };

    public void create(Genre genre) {
        repository.persist(genre);
    };

    public void update(String id, Genre genre) {
        Genre oldGenre = this.findById(id);

        oldGenre.setName(genre.getName());
        repository.update(oldGenre);
    };
}
