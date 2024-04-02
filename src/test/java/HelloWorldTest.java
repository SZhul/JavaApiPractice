import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;

import java.util.HashMap;
import java.util.Map;

public class HelloWorldTest {

    @Test
    public void testRestAssured() {

        Map<String, String> params = new HashMap<>();
        params.put("name", "Sergey");

        JsonPath response = RestAssured
                .given()
                .queryParams(params)
                .get("http://playground.learnqa.ru/api/hello")
                .jsonPath();

        String name = response.get("answer");
        if (name == null) {
            System.out.println("The key 'answer2' is absent");
        } else {
            System.out.println(name);
        }
    }
}
