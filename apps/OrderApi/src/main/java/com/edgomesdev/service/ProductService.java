package com.edgomesdev.service;

import com.edgomesdev.exception.NotFoundException;
import com.edgomesdev.model.Order;
import com.edgomesdev.model.Product;
import com.edgomesdev.repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductService {
    @Inject
    ProductRepository repository;

    @Inject
    OrderService orderService;

    public List<Product> findAll() {
        return repository.listAll();
    };

    public Product findById(String id) {
        ObjectId productId = new ObjectId(id);
        Optional<Product> product = repository.findByIdOptional(productId);

        return product.orElseThrow(() -> new NotFoundException("Product não encontrado"));
    };

    public Product create(String orderId, Product product) {
        product.persist();

        Order order = orderService.findById(orderId);

        order.addProduct(product);
        order.persistOrUpdate();

        return product;
    };

    public Product update(String id, Product product) {
        Product oldProduct = this.findById(id);

        oldProduct.setTitle(product.getTitle());
        oldProduct.setPublisher(product.getPublisher());
        oldProduct.setReleaseYear(product.getReleaseYear());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setQuantity(product.getQuantity());

        oldProduct.update();

        return oldProduct;
    };
}
