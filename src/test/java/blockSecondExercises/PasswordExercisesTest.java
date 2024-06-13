package blockSecondExercises;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Нужно найти верный пароль среди тех, что представлены в списке.
 */

public class PasswordExercisesTest {

    @Test
    public void testLongResponse() {

        for (int i = 0; i < 26; i++) {
            ArrayList<String> passwords = new ArrayList<>();
            passwords.add("123456");
            passwords.add("123456789");
            passwords.add("qwerty");
            passwords.add("password");
            passwords.add("1234567");
            passwords.add("12345678");
            passwords.add("12345");
            passwords.add("iloveyou");
            passwords.add("111111");
            passwords.add("123123");
            passwords.add("abc123");
            passwords.add("qwerty123");
            passwords.add("1q2w3e4r");
            passwords.add("admin");
            passwords.add("qwertyuiop");
            passwords.add("654321");
            passwords.add("555555");
            passwords.add("lovely");
            passwords.add("7777777");
            passwords.add("welcome");
            passwords.add("888888");
            passwords.add("princess");
            passwords.add("dragon");
            passwords.add("password1");
            passwords.add("123qwe");

            Map<String, String> params = new HashMap<>();
            params.put("login", "super_admin");
            params.put("password", passwords.get(i));

            Response response = RestAssured
                    .given()
                    .body(params)
                    .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                    .andReturn();

            System.out.println("\n Response:");
            response.prettyPrint();

            System.out.println("\n Cookies in one string:");
            String authCookie = response.getCookie("auth_cookie");
            System.out.println(authCookie);

            Response responseWithCookie = RestAssured
                    .given()
                    .cookies("auth_cookie", authCookie)
                    .post("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                    .andReturn();

            System.out.println("\n Response with cookie:");
            responseWithCookie.print();
            String answer = responseWithCookie.asString();
            System.out.println(answer);
            if(answer.equals("You are authorized")) {
                System.out.println("\nПравильный пароль: " + passwords.get(i));
                break;
            }
        }
    }
}
