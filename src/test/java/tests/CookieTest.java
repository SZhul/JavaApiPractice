package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CookieTest {

    @Test
    public void testGetCookies() {
        Response response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();

        System.out.println("\n Cookies: " + response.getCookies());
        String cookie = response.getCookie("HomeWork");
        assertEquals(cookie, "hw_value", "Cookie value is not equal to expected value");
    }
}
