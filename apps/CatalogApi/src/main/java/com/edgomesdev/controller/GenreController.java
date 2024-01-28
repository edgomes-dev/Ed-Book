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
    public Response findAll() {
        return Response.status(Response.Status.OK).entity(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(String id) {
        return Response.status(Response.Status.OK).entity(service.findById(id)).build();
    }

    @POST
    public Response create(Genre genre) {
        return Response.status(Response.Status.CREATED).entity(service.create(genre)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(String id, Genre genre) {
        return Response.status(Response.Status.OK).entity(service.update(id, genre)).build();
    }
}
