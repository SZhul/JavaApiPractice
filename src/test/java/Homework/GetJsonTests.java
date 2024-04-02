package Homework;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * В рамках кейса проходим следующий тест:
 * 1. Отправляем get-запрос на <a href="https://playground.learnqa.ru/api/get_json_homework">...</a>
 * 2. Смотрим, какой json оттуда приходит, выводя его в список ArrayList
 * 3. Через коллекцию HashMap выводим в консоль только значение второго ключа из массива
 **/

public class GetJsonTests {

    @Test
    public void testGetHomework() {
        Response response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .andReturn();

        response.prettyPrint();
    }

    @Test
    public void testGetJsonHomework() {
        JsonPath response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();

        ArrayList<String> list = response.get("messages");
        if (list == null) {
            System.out.println("The key is absent");
        } else {
            System.out.println(list);
        }

        HashMap<String, String> name = response.get("messages[1]");
        if (name == null) {
            System.out.println("The key is absent");
        } else {
            System.out.println(name);
        }
    }
}

