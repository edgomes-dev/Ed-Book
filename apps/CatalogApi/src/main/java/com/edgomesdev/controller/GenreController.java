package com.edgomesdev.controller;

import com.edgomesdev.model.Genre;
import com.edgomesdev.service.GenreService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/genres")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GenreController {
    @Inject
    GenreService service;

    @GET
    public List<Genre> findAll() {
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public Genre findById(String id) {
        return service.findById(id);
    }

    @POST
    public Response create(Genre genre) {
        genre.persist();
        return Response.created(URI.create("/genres/" + genre.id)).build();
    }

    @PUT
    @Path("/{id}")
    public void update(String id, Genre genre) {
        service.update(id, genre);
    }
}
