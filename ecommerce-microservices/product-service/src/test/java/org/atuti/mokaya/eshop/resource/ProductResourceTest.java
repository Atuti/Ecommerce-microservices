package org.atuti.mokaya.eshop.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ProductResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/products")
          .then()
             .statusCode(200)
             .body(is("Hello from RESTEasy Reactive"));
    }

}