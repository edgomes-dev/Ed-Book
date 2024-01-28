package com.edgomesdev.service;

import com.edgomesdev.exception.NotFoundException;
import com.edgomesdev.model.Genre;
import com.edgomesdev.repository.GenreRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;


@QuarkusTest
public class GenreServiceTest {
    @Inject
    GenreService service;

    @InjectMock
    GenreRepository repository;

    private final ObjectId testId = new ObjectId("65aa4f6fedcec84eeee861ee");

    private final List<Genre> genreList = Arrays.asList(new Genre(testId, "Terror", null), new Genre(new ObjectId("65aa4f6fedcec84eeee861dd"), "Romance", null));

    private final Genre newGenre = new Genre(null, "Romance", null);

    private final Genre genreExists = new Genre(testId, "Romance", null);

    @BeforeEach
    void setUp() {
    }

    @Test
    public void should_findAllSucess() {

        when(repository.listAll())
                .thenReturn(genreList);
        Assertions.assertEquals(2, service.findAll().size());
    };

    @Test
    public void should_findByIdSucess() {
        when(repository.findByIdOptional(testId))
                .thenReturn(Optional.of(genreExists));

        Genre entity = service.findById(testId.toString());

        Assertions.assertEquals("Romance", entity.getName());
    };

    @Test
    public void should_findByIdFailure_whenIdInvalid() {
        Assertions.assertThrows(NotFoundException.class, () -> service.findById(testId.toString()));
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.findById("teste"));
    };

    @Test
    public void should_createSucess() {
        Genre createdGenre = service.create(newGenre);

        Assertions.assertNotNull(createdGenre);
        Assertions.assertNotNull(createdGenre.getId());
        Assertions.assertEquals("Romance", createdGenre.getName());
    };

    @Test
    public void should_updatSucess() {

        when(repository.findByIdOptional(testId))
                .thenReturn(Optional.of(genreExists));

        Genre updatedGenre = new Genre(testId, "Updated Romance", null);
        Genre result = service.update("65aa4f6fedcec84eeee861ee", updatedGenre);

        Assertions.assertEquals("Updated Romance", result.getName());
    };
}
