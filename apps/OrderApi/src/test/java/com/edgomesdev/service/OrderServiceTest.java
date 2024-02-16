package com.edgomesdev.service;

import com.edgomesdev.exception.NotFoundException;
import com.edgomesdev.model.Order;
import com.edgomesdev.model.Status;
import com.edgomesdev.repository.OrderRepository;
import com.edgomesdev.service.OrderService;
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
public class OrderServiceTest {
    @Inject
    OrderService service;

    @InjectMock
    OrderRepository repository;

    private final ObjectId testId = new ObjectId("65aa4f6fedcec84eeee861ee");

    private final Order newOrder = new Order(null, 20.0, Status.PENDING, null);

    private final Order orderExists = new Order(testId, 20.0, Status.PENDING, null);

    private final List<Order> orderList = Arrays.asList(orderExists, new Order(new ObjectId("65aa4f6fedcec84eeee861dd"), 45.0, Status.SENT, null));
    
    @Test
    public void should_findAllSucess() {
        when(repository.listAll())
                .thenReturn(orderList);
        Assertions.assertEquals(2, service.findAll().size());
    }

    @Test
    public void should_findByIdSucess() {
        when(repository.findByIdOptional(testId))
                .thenReturn(Optional.of(orderExists));

        Order entity = service.findById(testId.toString());

        Assertions.assertEquals(20.0, entity.getTotalPrice());
    };

    @Test
    public void should_findByIdFailure_whenIdInvalid() {
        Assertions.assertThrows(NotFoundException.class, () -> service.findById(testId.toString()));
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.findById("teste"));
    };

    @Test
    public void should_createSucess() {
        Order createdOrder = service.create(newOrder);

        Assertions.assertNotNull(createdOrder);
        Assertions.assertNotNull(createdOrder.getId());
        Assertions.assertEquals(20.0, createdOrder.getTotalPrice());
    };
}
