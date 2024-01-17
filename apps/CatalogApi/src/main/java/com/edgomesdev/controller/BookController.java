package com.edgomesdev.controller;

import com.edgomesdev.model.Book;
import com.edgomesdev.service.BookService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/books")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookController {
    @Inject
    BookService service;

    @GET
    public List<Book> findAll() {
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public Book findById(String id) {
        return service.findById(id);
    }

    @POST
    public Response create(Book book) {
        book.persist();
        return Response.created(URI.create("/books/" + book.id)).build();
    }

    @PUT
    @Path("/{id}")
    public void update(String id, Book book) {
        service.update(id, book);
    }
}
