package com.edgomesdev.controller;

import com.edgomesdev.model.Product;
import com.edgomesdev.service.ProductService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductController {
    @Inject
    ProductService service;

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
    public Response create(String id, Product product) {
        return Response.status(Response.Status.CREATED).entity(service.create(id, product)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(String id, Product product) {
        return Response.status(Response.Status.OK).entity(service.update(id, product)).build();
    }
}
