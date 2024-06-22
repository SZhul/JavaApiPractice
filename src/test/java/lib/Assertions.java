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

    public static void assertResponseTextEquals(Response response, String expectedAnswer) {
        assertEquals(expectedAnswer, response.asString(), "Response text is not equal to expected value");
    }

    public static void assertResponseStatusEquals(Response response, int expectedStatus) {
        assertEquals(expectedStatus,
                response.getStatusCode(),
                "Expected status code is not equal to actual status code");
    }

    public static void assertJsonHasKey(Response response, String expectedFieldName) {
        response.then().assertThat().body("$", hasKey(expectedFieldName));
    }


}
