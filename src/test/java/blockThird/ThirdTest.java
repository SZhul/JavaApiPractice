package blockThird;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ThirdTest {

    @Test
    public void AuthorizationPositiveTest() {
        Map<String, String> data = new HashMap<>();
        data.put("email", "vinkotov@example.com");
        data.put("password", "1234");

        Response responseToAuth = RestAssured
                .given()
                .body(data)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        Map<String, String> cookies = responseToAuth.getCookies();
        Headers headers = responseToAuth.getHeaders();
        int userId = responseToAuth.jsonPath().get("user_id");

        assertEquals(200, responseToAuth.statusCode(), "Unexpected status code");
        assertTrue(cookies.containsKey("auth_sid"), "The cookie 'auth_sid'is not found");
        assertTrue(headers.hasHeaderWithName("x-csrf-token"), "The header 'x-csrf-token'is not found");
        assertTrue(responseToAuth.jsonPath().getInt("user_id") > 0, "The user_id is should be greater than 0");

        JsonPath responseToCheckAuth = RestAssured
                .given()
                .header("x-csrf-token", responseToAuth.getHeader("x-csrf-token"))
                .cookie("auth_sid", responseToAuth.getCookie("auth_sid"))
                .get("https://playground.learnqa.ru/api/user/auth")
                .jsonPath();

        int userIdAfterCheckAuth = responseToCheckAuth.getInt("user_id");
        assertTrue(userIdAfterCheckAuth > 0, "Unexpected user_id " + userIdAfterCheckAuth);
        assertEquals(userIdAfterCheckAuth, userId, "user id's not equals");
    }

    @ParameterizedTest
    @ValueSource(strings = {"cookie", "headers"})
    public void authorizationNegativeTest(String condition){
        Map<String, String> data = new HashMap<>();
        data.put("email", "vinkotov@example.com");
        data.put("password", "1234");

        Response responseToAuth = RestAssured
                .given()
                .body(data)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        Map<String, String> cookies = responseToAuth.getCookies();
        Headers headers = responseToAuth.getHeaders();

        RequestSpecification spec = RestAssured.given();
        spec.baseUri("https://playground.learnqa.ru/api/user/auth");

        if(condition.equals("cookie")){
            spec.cookie("auth_sid", cookies.get("auth_sid"));
        } else if(condition.equals("headers")){
            spec.header("x-csrf-token", headers.get("x-csrf-token"));
        } else {
            throw new IllegalArgumentException("Condition values is known " + condition);
        }

        JsonPath responseForCheck = spec.get().jsonPath();
        assertEquals(0, responseForCheck.getInt("user_id"), "User_id should be 0");
    }
}
