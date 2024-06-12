package blockSecond;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class FirstJsonParseTest {

    @Test
    public void testJsonParse() {

        Map<String, String> params = new HashMap<>();
        params.put("name", "Sergey");

        JsonPath response = RestAssured
                .given()
                .queryParams(params)
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();

        String name = response.get("answer");
        System.out.println(name);

        String name2 = response.get("name");
        if (name2 == null) {
            System.out.println("Значение ключа 'name' равно null");
        } else {
            System.out.println(name2);
        }
    }
}
