package com.edgomesdev.controller;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@TestHTTPEndpoint(ProductController.class)
public class ProductControllerTest {

}
