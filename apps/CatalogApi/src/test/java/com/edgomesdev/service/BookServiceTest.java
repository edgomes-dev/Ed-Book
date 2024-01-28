package com.edgomesdev.service;

import com.edgomesdev.exception.NotFoundException;
import com.edgomesdev.model.Book;
import com.edgomesdev.repository.BookRepository;
import com.edgomesdev.repository.BookRepository;
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
public class BookServiceTest {
    @Inject
    BookService service;

    @InjectMock
    BookRepository repository;

    private final ObjectId testId = new ObjectId("65aa4f6fedcec84eeee861ee");

    private final Book newBook = new Book(null, "Procura-Se Um Coração", "Saraiva", "2012", "O tempo que Ariane tem de vida é bem menor do que se imagina.", 10);

    private final Book bookExists = new Book(testId, "Procura-Se Um Coração", "Saraiva", "2012", "O tempo que Ariane tem de vida é bem menor do que se imagina.", 10);

    private final List<Book> bookList = Arrays.asList(bookExists, new Book(new ObjectId("65ac4f6ecdbad84eeee861cd"), "Pouso do sossego", "Saraiva", "2014", "Este é o segundo volume da trilogia Tempus fugit, que teve início com Tapete de silêncio.", 10));

    @BeforeEach
    void setUp() {
    }

    @Test
    public void should_findAllSucess() {

        when(repository.listAll())
                .thenReturn(bookList);
        Assertions.assertEquals(2, service.findAll().size());
    };

    @Test
    public void should_findByIdSucess() {
        when(repository.findByIdOptional(testId))
                .thenReturn(Optional.of(bookExists));

        Book entity = service.findById(testId.toString());

        Assertions.assertEquals("Procura-Se Um Coração", entity.getTitle());
    };

    @Test
    public void should_findByIdFailure_whenIdInvalid() {
        Assertions.assertThrows(NotFoundException.class, () -> service.findById(testId.toString()));
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.findById("teste"));
    };

    @Test
    public void should_createSucess() {
        Book createdBook = service.create("65aa4f6acdaec84abef861ff",newBook);

        Assertions.assertNotNull(createdBook);
        Assertions.assertNotNull(createdBook.getId());
        Assertions.assertEquals("Procura-Se Um Coração", createdBook.getTitle());
    };

    @Test
    public void should_updatSucess() {

        when(repository.findByIdOptional(testId))
                .thenReturn(Optional.of(bookExists));

        Book updatedBook = bookExists;
        updatedBook.setTitle("Encontrei um coração");
        Book result = service.update(testId.toString(), updatedBook);

        Assertions.assertEquals("Encontrei um coração", result.getTitle());
    };
}
