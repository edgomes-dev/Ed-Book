package com.edgomesdev.controller;

import com.edgomesdev.exception.NotFoundException;
import com.edgomesdev.model.Order;
import com.edgomesdev.model.Status;
import com.edgomesdev.service.OrderService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestHTTPEndpoint(OrderController.class)
public class OrderControllerTest {
    @InjectMock
    OrderService service;

    private final ObjectId testId = new ObjectId("65aa4f6fedcec84eeee861ee");

    private final Order newOrder = new Order(null, 20.0, Status.PENDING, null);

    private final Order orderExists = new Order(testId, 20.0, Status.PENDING, null);

    private final List<Order> orderList = Arrays.asList(orderExists, new Order(new ObjectId("65aa4f6fedcec84eeee861dd"), 45.0, Status.SENT, null));

    @Test
    public void should_findAllSucess() {
        when(service.findAll()).thenReturn(orderList);

        given().when().get()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$.size()", equalTo(2));

        verify(service).findAll();
    }

    @Test
    public void should_findByIdSucess() {
        when(service.findById(testId.toString())).thenReturn(orderExists);

        given().pathParam("id", testId.toString()).when().get("/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("total_price", equalTo(20.0));

        verify(service).findById(testId.toString());
    }

    @Test
    public void should_findByIdFailure_whenIdInvalid() {
        when(service.findById(testId.toString())).thenThrow(new NotFoundException("ID inválido"));

        given().pathParam("id", testId.toString()).when().get("/{id}")
                .then().statusCode(Response.Status.NOT_FOUND.getStatusCode());

        verify(service).findById(testId.toString());
    }

    @Test
    public void should_findByIdFailure_whenIdInvalidFormat() {
        when(service.findById("test")).thenThrow(new IllegalArgumentException());

        given().pathParam("id", "test").when().get("/{id}")
                .then().statusCode(Response.Status.BAD_REQUEST.getStatusCode());

        verify(service).findById("test");
    }

    @Test
    public void should_createSucess() {
        when(service.create(any()))
                .thenReturn(orderExists);

        String json = "{\"total_price\":20}";

        given().body(json).header("Content-Type", "application/json").when().post().then().statusCode(201).body("total_price", equalTo(20.0));
    }
}
