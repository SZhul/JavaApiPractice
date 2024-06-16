package blockThird;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ThirdTest {

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

        this.cookie = responseToAuth.getCookie("auth_sid");
        this.header = responseToAuth.getHeader("x-csrf-token");
        this.userIdOnAuth = responseToAuth.jsonPath().getInt("user_id");
    }

    @Test
    public void AuthorizationPositiveTest() {

        JsonPath responseToCheckAuth = RestAssured
                .given()
                .header("x-csrf-token", this.header)
                .cookie("auth_sid", this.cookie)
                .get("https://playground.learnqa.ru/api/user/auth")
                .jsonPath();

        int userIdAfterCheckAuth = responseToCheckAuth.getInt("user_id");
        assertTrue(userIdAfterCheckAuth > 0, "Unexpected user_id " + userIdAfterCheckAuth);
        assertEquals(userIdAfterCheckAuth, userIdOnAuth, "user id's not equals");
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

        JsonPath responseForCheck = spec.get().jsonPath();
        assertEquals(0, responseForCheck.getInt("user_id"), "User_id should be 0");
    }
}
