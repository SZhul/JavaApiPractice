package blockFirst;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class SimpleGetRequestTest {

    @Test
    public void testSimpleGetRequest() {
        // создаем переменную response типа Response
        Response response = RestAssured
        // запрашиваем значение через get с эндпоинта
                .get("https://playground.learnqa.ru/api/hello")
        // возвращаем ответ через andReturn
                .andReturn();
        // поскольку ответ хранится внутри переменной response, выбираем метод  prettyPrint для вывода в консоль
        response.prettyPrint();
    }
}
