package lib;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Assertions {

    public static void assertJsonByName(Response response, String name, int expectedValue) {
        //проверяем, что во всем боди ответа есть ключ с таким именем
        response.then().assertThat().body("$", hasKey(name));

        //парсим json из ключа name в переменную value
        int value = response.jsonPath().getInt(name);

        //проверяем, что ожидаемое значение равно value
        assertEquals(expectedValue, value, "Json value is not equal to expected value");
    }
}
