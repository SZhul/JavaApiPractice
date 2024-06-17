package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import io.restassured.http.Headers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeaderTest {

    @Test
    public void testGeHeader() {
        Response response = RestAssured
                .given()
                .post("https://playground.learnqa.ru/api/homework_header")
                .andReturn();

        Headers headers = response.getHeaders();
        System.out.println("\n Headers: " + headers);
        String headerToAssert = headers.getValue("x-secret-homework-header");
        assertEquals(headerToAssert, "Some secret value");
    }
}
