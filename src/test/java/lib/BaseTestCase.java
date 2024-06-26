package lib;

import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.util.Map;

import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseTestCase {

    protected String getHeader(Response response, String name){
        Headers headers = response.getHeaders();

        assertTrue(headers.hasHeaderWithName(name), "Response doesn't have header with name " + name);
        return headers.getValue(name);
    }

    protected String getCookie(Response response, String name){
        Map<String, String> cookies = response.getCookies();

        assertTrue(cookies.containsKey(name), "Response dosen't have cookie with name " + name);
        return cookies.get(name);
    }

    protected int getIntFromJson(Response response, String name){
        // можно прочитать как: сделай запрос, после чего проверь (assert), что в body есть этот ключ, значок
        // доллара указывает на то, что мы смотрим по всему json
        response.then().assertThat().body("$", hasKey(name));
        return response.jsonPath().getInt(name);
    }
}
