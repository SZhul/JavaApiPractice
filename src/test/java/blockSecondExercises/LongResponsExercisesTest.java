package blockSecondExercises;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.lang.Thread;

public class LongResponsExercisesTest {

    @Test
    public void testLongResponse() throws InterruptedException {
        Response response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .andReturn();

        response.prettyPrint();
        String getToken = response.path("token");
        int seconds = response.path("seconds");

        Response responseWithToken = RestAssured
                .given()
                .queryParam("token", getToken)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .andReturn();

        responseWithToken.prettyPrint();
        String status = responseWithToken.path("status");


        if (status.equals("Job is NOT ready")) {
            Thread.sleep(seconds * 1000L);
            Response nextResponseWithToken = RestAssured
                    .given()
                    .queryParam("token", getToken)
                    .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                    .andReturn();
            nextResponseWithToken.prettyPrint();
            String endResult = nextResponseWithToken.path("result");
            String endStatus = nextResponseWithToken.path("status");
            if(endStatus.equals("Job is ready") && (endResult != null)) {
                System.out.println("Задача выполнена");
            } else {
                System.out.println("Задача не выполнена");
            }
        }
    }
}
