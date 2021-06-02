package com.zeinab.library;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LibraryE2ETests {

    final String basicAuthUsername = "test_username";
    final String basicAuthPassword = "test_password";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    void test_post_book_and_get_by_id(){

        given().auth()
                .preemptive()
                .basic(basicAuthUsername, basicAuthPassword)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(Fixtures.book1)
                .post("/api/v1/books")
                .then()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .assertThat()
                .statusCode(201);

        given().auth()
                .preemptive()
                .basic(basicAuthUsername, basicAuthPassword)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get("/api/v1/books/1")
                .then()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .assertThat()
                .statusCode(200);
    }

    @Test
    void test_post_books_and_get_all(){

        Fixtures.books.forEach(book -> {
            given().auth()
                    .preemptive()
                    .basic(basicAuthUsername, basicAuthPassword)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(book)
                    .post("/api/v1/books")
                    .then()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .assertThat()
                    .statusCode(201);
        });

        given().auth()
                .preemptive()
                .basic(basicAuthUsername, basicAuthPassword)
                .get("/api/v1/books")
                .then()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .assertThat()
                .body("size()", equalTo(3))
                .statusCode(200);
    }

    @Test
    void test_post_book_and_delete(){

        given().auth()
                .preemptive()
                .basic(basicAuthUsername, basicAuthPassword)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(Fixtures.book1)
                .post("/api/v1/books")
                .then()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .assertThat()
                .statusCode(201);

        given().auth()
                .preemptive()
                .basic(basicAuthUsername, basicAuthPassword)
                .delete("/api/v1/books/1")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void test_post_three_books_and_delete_one(){

        Fixtures.books.forEach(book -> {
            given().auth()
                    .preemptive()
                    .basic(basicAuthUsername, basicAuthPassword)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(book)
                    .post("/api/v1/books")
                    .then()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .assertThat()
                    .statusCode(201);
        });

        given().auth()
                .preemptive()
                .basic(basicAuthUsername, basicAuthPassword)
                .delete("/api/v1/books/1")
                .then()
                .assertThat()
                .statusCode(200);

        given().auth()
                .preemptive()
                .basic(basicAuthUsername, basicAuthPassword)
                .get("/api/v1/books")
                .then()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .assertThat()
                .body("size()", equalTo(2))
                .statusCode(200);
    }

    @Test
    void test_unauthorized_post_book(){

        given().auth()
                .preemptive()
                .basic("invalid-user", "invalid-password")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(Fixtures.book1)
                .post("/api/v1/books")
                .then()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .assertThat()
                .statusCode(401);
    }
}
