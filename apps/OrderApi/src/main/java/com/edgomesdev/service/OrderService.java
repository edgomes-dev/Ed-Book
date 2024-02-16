package com.edgomesdev.service;

import com.edgomesdev.exception.NotFoundException;
import com.edgomesdev.model.Order;
import com.edgomesdev.repository.OrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class OrderService {
    @Inject
    OrderRepository repository;

    public List<Order> findAll() {
        return repository.listAll();
    };

    public Order findById(String id) {
        ObjectId orderId = new ObjectId(id);
        Optional<Order> order = repository.findByIdOptional(orderId);

        return order.orElseThrow(() -> new NotFoundException("ID inválido"));
    };

    public Order create(Order order) {
        order.persist();

        return order;
    };
}
