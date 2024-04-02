import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class ServerCodesTests {

    @Test
    public void testGetCodes() {
        Response response = RestAssured
                .get("http://playground.learnqa.ru/api/check_type")
                .andReturn();
        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
    }

    @Test
    public void testPostRequestCode() {
        Response response = RestAssured
                .post("http://playground.learnqa.ru/api/check_type")
                .andReturn();
        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
    }

    @Test
    public void test500(){
        Response response = RestAssured
                .get("http://playground.learnqa.ru/api/get_500")
                .andReturn();

        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
    }

    @Test
    public void test303(){
        Response response = RestAssured
                .given()
                .redirects()
                .follow(true)
                .when()
                .get("http://playground.learnqa.ru/api/get_303")
                .andReturn();

        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
    }
}
