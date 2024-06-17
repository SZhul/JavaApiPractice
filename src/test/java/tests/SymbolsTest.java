package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import lib.BaseTestCase;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class SymbolsTest extends BaseTestCase {


    public String JsonParse() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "Sergey");

        JsonPath response = RestAssured
                .given()
                .queryParams(params)
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();

        String name = response.get("answer");
        return name;
    }

    @Test
    public void symbolsTest() {
        String randomText = JsonParse();
        int length = randomText.length();
        assertTrue(length <= 15, "The length of the text is greater than 15");
    }
}
