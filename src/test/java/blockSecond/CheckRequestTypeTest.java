package blockSecond;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class CheckRequestTypeTest {

    @Test()
    public void testCheckRequestTypeWithGet() {
        Response response = RestAssured
                .given()
                .queryParam("param1", "value1")
                .queryParams("param2", "value2")
                .get("https://playground.learnqa.ru/api/check_type")
                .andReturn();
        response.print();
    }

    @Test()
    public void testCheckRequestTypeWithPost() {
        Response response = RestAssured
                .given()
                .body("param1=value1&param2=value2")
                .post("https://playground.learnqa.ru/api/check_type")
                .andReturn();
        response.print();
    }


    @Test()
    public void testCheckRequestTypeWithPostWhenBodyIsJson() {
        Response response = RestAssured
                .given()
                .body("{\"param1\":\"value1\", \"param2\":\"value2\"}")
                .post("https://playground.learnqa.ru/api/check_type")
                .andReturn();
        response.print();
    }

    @Test()
    public void testCheckRequestTypeWithPostWhenBodyIsJsonWithObject() {
        Map<String, Object> body = new HashMap<>();
        body.put("param1", "value1");
        body.put("param2", "value2");

        Response response = RestAssured
                .given()
                .body(body)
                .post("https://playground.learnqa.ru/api/check_type")
                .andReturn();
        response.print();
    }
}
