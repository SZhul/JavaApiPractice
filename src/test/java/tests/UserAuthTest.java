package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lib.Assertions;
import lib.BaseTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

public class UserAuthTest extends BaseTestCase {
    String cookie;
    String header;
    int userIdOnAuth;

    @BeforeEach
    public void loginUser(){
        Map<String, String> data = new HashMap<>();
        data.put("email", "vinkotov@example.com");
        data.put("password", "1234");

        Response responseToAuth = RestAssured
                .given()
                .body(data)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        this.cookie = this.getCookie(responseToAuth, "auth_sid");
        this.header = this.getHeader(responseToAuth, "x-csrf-token");
        this.userIdOnAuth = this.getIntFromJson(responseToAuth, "user_id");
    }

    @Test
    public void AuthorizationPositiveTest() {

        Response responseToCheckAuth = RestAssured
                .given()
                .header("x-csrf-token", this.header)
                .cookie("auth_sid", this.cookie)
                .get("https://playground.learnqa.ru/api/user/auth")
                .andReturn();

        Assertions.assertJsonByName(responseToCheckAuth, "user_id", this.userIdOnAuth);


    }

    @ParameterizedTest
    @ValueSource(strings = {"cookie", "headers"})
    public void authorizationNegativeTest(String condition){

        RequestSpecification spec = RestAssured.given();
        spec.baseUri("https://playground.learnqa.ru/api/user/auth");

        if(condition.equals("cookie")){
            spec.cookie("auth_sid", this.cookie);
        } else if(condition.equals("headers")){
            spec.header("x-csrf-token", this.header);
        } else {
            throw new IllegalArgumentException("Condition values is known " + condition);
        }

        Response responseForCheck = spec.get().andReturn();
        Assertions.assertJsonByName(responseForCheck, "user_id", 0);
    }
}
