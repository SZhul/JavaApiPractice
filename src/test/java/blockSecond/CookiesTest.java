package blockSecond;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * В этом тесте сначала получаем авторизационную куку, а затем передаем ее в следующем запросе
 */

public class CookiesTest {

    @Test
    public void testGetCookies() {
        Map<String, String> data = new HashMap<>();
        data.put("login", "secret_login");
        data.put("password", "secret_pass");

        Response response = RestAssured
                .given()
                .body(data)
                .when()
                .post("https://playground.learnqa.ru/api/get_auth_cookie")
                .andReturn();

        System.out.println("\n Response:");
        response.prettyPrint();

        System.out.println("\n Cookies:");
        Map<String, String> cookies = response.getCookies();
        System.out.println(cookies);

        System.out.println("\n Cookies in one string:");
        String authCookie = response.getCookie("auth_cookie");
        System.out.println(authCookie);
    }

    @Test
    public void testSetCookies() {
        Map<String, String> data = new HashMap<>();
        data.put("login", "secret_login");
        data.put("password", "secret_pass");

        Response responseForGet = RestAssured
                .given()
                .body(data)
                .when()
                .post("https://playground.learnqa.ru/api/get_auth_cookie")
                .andReturn();

        System.out.println("\n Cookies in one string:");
        String authCookie = responseForGet.getCookie("auth_cookie");
        System.out.println(authCookie);

        // делаем простую проверку -> если кука пришла пустой, то не записываем ее в Map
        Map<String, String> dataForCheck = new HashMap<>();
        if(authCookie != null){
            dataForCheck.put("auth_cookie", authCookie);
        }

        Response responseForCheck = RestAssured
                .given()
                .body(data)
                .cookies(dataForCheck)
                .when()
                .get("https://playground.learnqa.ru/api/check_auth_cookie")
                .andReturn();

        responseForCheck.print();
    }
}
