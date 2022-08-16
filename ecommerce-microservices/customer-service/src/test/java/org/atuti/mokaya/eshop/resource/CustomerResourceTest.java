package org.atuti.mokaya.eshop.resource;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class CustomerResourceTest {

    // @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/customers")
          .then()
             .statusCode(200)
             .body(is("Hello from RESTEasy Reactive"));
    }

}