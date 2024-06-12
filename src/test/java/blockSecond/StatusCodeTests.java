package blockSecond;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

/**
 * В этих тестах получаем статус-коды ответа
 */

public class StatusCodeTests {

    @Test
    public void testStatusCode() {
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/hello")
                .andReturn();

        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
    }

    @Test
    public void testStatusCode500() {
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/get_500")
                .andReturn();

        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
    }

    @Test
    public void testStatusCode404() {
        Response response = RestAssured
                //отправляем get-запрос на метод, которого не существует
                .get("https://playground.learnqa.ru/api/404")
                .andReturn();

        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
    }

    @Test
    public void testStatusCode303() {
        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/get_303")
                .andReturn();

        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
    }
}
