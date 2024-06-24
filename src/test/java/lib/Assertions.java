package lib;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
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

    public static void assertJsonByName(Response response, String name, String expectedValue) {
        //проверяем, что во всем боди ответа есть ключ с таким именем
        response.then().assertThat().body("$", hasKey(name));

        //парсим json из ключа name в переменную value
        String value = response.jsonPath().getString(name);

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

    public static void assertJsonHasField(Response response, String expectedFieldName) {
        response.then().assertThat().body("$", hasKey(expectedFieldName));
    }

    public static void assertJsonHasFields(Response response, String[] expectedFieldNames) {
        for (String fieldName : expectedFieldNames) {
            Assertions.assertJsonHasField(response, fieldName);
        }
    }

    public static void assertJsonHasNotField(Response response, String notExpectedFieldName) {
        response.then().assertThat().body("$", not(hasKey(notExpectedFieldName)));
    }


}
