package blockSecondExercises;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class JsonParseExercisesTest {

    @Test
    public void testSimpleGetRequest() {
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .andReturn();

        response.prettyPrint();

        JsonPath jsonResponse = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();

        String message = jsonResponse.get("messages[1].message");
        String timestamp = jsonResponse.get("messages[1].timestamp");
        System.out.println(message + " \n" + timestamp);
    }
}
