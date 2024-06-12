package blockSecondExercises;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class LongRedirectExercisesTest {

    @Test
    public void testLongRedirectExercises() {

        String url = "https://playground.learnqa.ru/api/long_redirect";

        while (true) {
            Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get(url);

            if (response.getStatusCode() == 200) {
                System.out.println("Конечный URL: " + url);
                break;
            }
            if (response.getHeader("Location") != null) {
                String redirectUrl = response.getHeader("Location");
                System.out.println("Редирект на URL: " + redirectUrl);
                url = redirectUrl;
            } else {
                System.out.println("Ошибка: нет заголовка Location");
                break;
            }
        }
    }
}

