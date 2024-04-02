package Homework;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import io.qameta.allure.Description;


/**
 * В рамках кейса смотрим 2 сценария:
 * 1. В TestRedirect мы не переходим по редиректу, проверяем код ответа и то, куда нас перенаправляет - для этого
 * смотрим на заголовок Location.
 * 2. Поскольку в сценарии 1 в коде ответа мы получаем 301, то в сценарии два пишем цикл, в котором мы переходим по
 * редиректу до того момента, пока код ответа не будет равен 200.
 */

public class LongRedirectTests {

    int statusCode;

    @Test
    @Description("Переходим по редиректу")
    public void testRedirect() {
        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        response.prettyPrint();

        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
        String locationHeader = response.getHeader("Location");
        System.out.println(locationHeader);
    }

    @Test
    public void testLongRedirect() {

        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        response.prettyPrint();

        statusCode = response.getStatusCode();
        System.out.println(statusCode);
        while (statusCode != 200){
            Response response1 = RestAssured
                    .given()
                    .redirects()
                    .follow(true)
                    .when()
                    .get("https://playground.learnqa.ru/api/long_redirect")
                    .andReturn();
            this.statusCode = response1.getStatusCode();
            String locationHeader = response.getHeader("Location");
            System.out.println(locationHeader);
            System.out.println(this.statusCode);
        }
        String locationHeader = response.getHeader("Location");
        System.out.println(locationHeader);
    }
}
