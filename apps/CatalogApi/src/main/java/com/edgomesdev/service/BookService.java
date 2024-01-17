package com.edgomesdev.service;

import com.edgomesdev.model.Book;
import com.edgomesdev.repository.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BookService {
    @Inject
    BookRepository repository;

    public List<Book> findAll() {
        return repository.listAll();
    };

    public Book findById(String id) {
        ObjectId bookId = new ObjectId(id);
        Optional<Book> book = repository.findByIdOptional(bookId);

        return book.orElseThrow(NotFoundException::new);
    };

    public void create(Book book) {
        repository.persist(book);
    };

    public void update(String id, Book book) {
        Book oldBook = this.findById(id);

        oldBook.setTitle(book.getTitle());
        oldBook.setPublisher(book.getPublisher());
        oldBook.setReleaseYear(book.getReleaseYear());
        oldBook.setDescription(book.getDescription());
        oldBook.setStock(book.getStock());

        repository.update(oldBook);
    };
}
