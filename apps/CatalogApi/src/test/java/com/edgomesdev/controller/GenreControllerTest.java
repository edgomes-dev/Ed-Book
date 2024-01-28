package com.edgomesdev.controller;

import com.edgomesdev.exception.NotFoundException;
import com.edgomesdev.model.Genre;
import com.edgomesdev.service.GenreService;
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
@TestHTTPEndpoint(GenreController.class)
public class GenreControllerTest {
    @InjectMock
    GenreService service;

    private final ObjectId testId = new ObjectId("65aa4f6fedcec84eeee861ee");

    private final List<Genre> genreList = Arrays.asList(new Genre(testId, "Terror", null), new Genre(new ObjectId("65aa4f6fedcec84eeee861dd"), "Romance", null));

    private final Genre newGenre = new Genre(null, "Romance", null);

    private final Genre genreExists = new Genre(testId, "Romance", null);

    @Test
    public void should_findAllSucess() {
        when(service.findAll()).thenReturn(genreList);

        given().when().get()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$.size()", equalTo(2));

        verify(service).findAll();
    }

    @Test
    public void should_findByIdSucess() {
        when(service.findById(testId.toString())).thenReturn(genreExists);

        given().pathParam("id", testId.toString()).when().get("/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("name", equalTo("Romance"));

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
                .thenReturn(genreExists);

        String json = "{\"name\":\"Romance\"}";

        given().body(json).header("Content-Type", "application/json").when().post().then().statusCode(201).body("name", equalTo("Romance"));
    }

    @Test
    public void should_updateSucess() {
        genreExists.setName("Updated Romance");
        when(service.update(any(), any()))
                .thenReturn(genreExists);

        String json = "{\"name\":\"Updated Romance\"}";

        given().pathParam("id", testId.toString())
                .body(json)
                .header("Content-Type", "application/json")
                .when().put("/{id}").then()
                .contentType(ContentType.JSON)
                .body("name", equalTo("Updated Romance"));
    }
}
