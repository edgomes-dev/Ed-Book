package com.edgomesdev.service;

import com.edgomesdev.exception.NotFoundException;
import com.edgomesdev.model.Book;
import com.edgomesdev.model.Genre;
import com.edgomesdev.repository.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BookService {
    @Inject
    BookRepository repository;

    @Inject
    GenreService genreService;

    public List<Book> findAll() {
        return repository.listAll();
    };

    public Book findById(String id) {
        ObjectId bookId = new ObjectId(id);
        Optional<Book> book = repository.findByIdOptional(bookId);

        return book.orElseThrow(() -> new NotFoundException("Book não encontrado"));
    };

    public Book create(String genreId, Book book) {
        book.persist();

        Genre genre = genreService.findById(genreId);

        genre.addBook(book);
        genre.persistOrUpdate();

        return book;
    };

    public Book update(String id, Book book) {
        Book oldBook = this.findById(id);

        oldBook.setTitle(book.getTitle());
        oldBook.setPublisher(book.getPublisher());
        oldBook.setReleaseYear(book.getReleaseYear());
        oldBook.setDescription(book.getDescription());
        oldBook.setStock(book.getStock());

        oldBook.update();

        return oldBook;
    };
}
