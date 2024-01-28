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
    public Response findAll() {
        return Response.status(Response.Status.OK).entity(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(String id) {
        return Response.status(Response.Status.OK).entity(service.findById(id)).build();
    }

    @POST
    @Path("/{id}")
    public Response create(String id, Book book) {
        System.out.println(book);
        return Response.status(Response.Status.CREATED).entity(service.create(id, book)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(String id, Book book) {
        return Response.status(Response.Status.OK).entity(service.update(id, book)).build();
    }
}
