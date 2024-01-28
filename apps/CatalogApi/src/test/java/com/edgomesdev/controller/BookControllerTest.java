package com.edgomesdev.controller;

import com.edgomesdev.exception.NotFoundException;
import com.edgomesdev.model.Book;
import com.edgomesdev.model.Genre;
import com.edgomesdev.service.BookService;
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
@TestHTTPEndpoint(BookController.class)
public class BookControllerTest {
    @InjectMock
    BookService service;

    private final ObjectId testId = new ObjectId("65aa4f6fedcec84eeee861ee");

    private final Book newBook = new Book(null, "Procura-Se Um Coração", "Saraiva", "2012", "O tempo que Ariane tem de vida é bem menor do que se imagina.", 10);

    private final Book bookExists = new Book(testId, "Procura-Se Um Coração", "Saraiva", "2012", "O tempo que Ariane tem de vida é bem menor do que se imagina.", 10);

    private final List<Book> bookList = Arrays.asList(bookExists, new Book(new ObjectId("65ac4f6ecdbad84eeee861cd"), "Pouso do sossego", "Saraiva", "2014", "Este é o segundo volume da trilogia Tempus fugit, que teve início com Tapete de silêncio.", 10));

    @Test
    public void should_findAllSucess() {
        when(service.findAll()).thenReturn(bookList);

        given().when().get()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$.size()", equalTo(2));

        verify(service).findAll();
    }

    @Test
    public void should_findByIdSucess() {
        when(service.findById(testId.toString())).thenReturn(bookExists);

        given().pathParam("id", testId.toString()).when().get("/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title", equalTo("Procura-Se Um Coração"));

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
        when(service.create(any(), any()))
                .thenReturn(bookExists);

        String json = "{\"title\":\"Procura-Se Um Coração\",\"publisher\":\"Saraiva\",\"releaseYear\":\"2012\",\"description\":\"O tempo que Ariane tem de vida é bem menor do que se imagina.\",\"stock\":10}";

        given().pathParam("id", "test").body(json).header("Content-Type", "application/json").when().post("/{id}").then().statusCode(201).body("title", equalTo("Procura-Se Um Coração"));
    }

    @Test
    public void should_updateSucess() {
        bookExists.setTitle("Encontrei um coração");
        when(service.update(any(), any()))
                .thenReturn(bookExists);

        String json = "{\"title\":\"Encontrei um coração\"}";

        given().pathParam("id", testId.toString())
                .body(json)
                .header("Content-Type", "application/json")
                .when().put("/{id}").then()
                .contentType(ContentType.JSON)
                .body("title", equalTo("Encontrei um coração"));
    }
}
