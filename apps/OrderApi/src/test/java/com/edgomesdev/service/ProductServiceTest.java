package com.edgomesdev.service;

import com.edgomesdev.exception.NotFoundException;
import com.edgomesdev.model.Product;
import com.edgomesdev.model.Status;
import com.edgomesdev.repository.ProductRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@QuarkusTest
public class ProductServiceTest {
    @Inject
    ProductService service;

    @InjectMock
    ProductRepository repository;

    private final ObjectId testId = new ObjectId("65aa4f6fedcec84eeee861ee");

    private final Product newProduct = new Product(null, "Procura-Se Um Coração", "Saraiva", "2012", "O tempo que Ariane tem de vida é bem menor do que se imagina.", 1);

    private final Product productExists = new Product(testId, "Procura-Se Um Coração", "Saraiva", "2012", "O tempo que Ariane tem de vida é bem menor do que se imagina.", 1);

    private final List<Product> productList = Arrays.asList(productExists, new Product(new ObjectId("65ac4f6ecdbad84eeee861cd"), "Pouso do sossego", "Saraiva", "2014", "Este é o segundo volume da trilogia Tempus fugit, que teve início com Tapete de silêncio.", 2));

    @Test
    public void should_findAllSucess() {
        when(repository.listAll())
                .thenReturn(productList);
        Assertions.assertEquals(2, service.findAll().size());
    }

    @Test
    public void should_findByIdSucess() {
        when(repository.findByIdOptional(testId))
                .thenReturn(Optional.of(productExists));

        Product entity = service.findById(testId.toString());

        Assertions.assertEquals("Procura-Se Um Coração", entity.getTitle());
    };

    @Test
    public void should_findByIdFailure_whenIdInvalid() {
        Assertions.assertThrows(NotFoundException.class, () -> service.findById(testId.toString()));
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.findById("teste"));
    };

    @Test
    public void should_createSucess() {
        Product createdProduct = service.create("65aa4f6acdaec84abef861ff", newProduct);

        Assertions.assertNotNull(createdProduct);
        Assertions.assertNotNull(createdProduct.getId());
        Assertions.assertEquals("Procura-Se Um Coração", createdProduct.getTitle());
    };
}
