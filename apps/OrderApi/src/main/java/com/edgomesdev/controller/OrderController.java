package com.edgomesdev.controller;

import com.edgomesdev.model.Order;
import com.edgomesdev.service.OrderService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderController {
    @Inject
    OrderService service;

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
    public Response create(Order order) {
        return Response.status(Response.Status.CREATED).entity(service.create(order)).build();
    }

    /*@PUT
    @Path("/{id}")
    public Response update(String id, Order order) {
        return Response.status(Response.Status.OK).entity(service.update(id, order)).build();
    }*/
}
