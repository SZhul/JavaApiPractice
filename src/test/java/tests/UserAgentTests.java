package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserAgentTests {

    private static Stream<Arguments> provideStringsForIsBlank() {
        return Stream.of(
                Arguments.of("'Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30'", true),
                Arguments.of("''Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1'", true),
                Arguments.of("'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)'", true),
                Arguments.of("'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0'", true),
                Arguments.of("'Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1'", true)
        );
    }

    @ParameterizedTest
    @MethodSource("provideStringsForIsBlank")
    public void UserAgentTest(String userAgent) {

        JsonPath userAgentInResponse = RestAssured
                .given()
                .header("User-Agent", userAgent)
                .get("https://playground.learnqa.ru/ajax/api/user_agent_check")
                .jsonPath();

        String agent = userAgentInResponse.get("user_agent");

        switch (agent) {
            case "'Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30'":
                assertUserAgentDetails(userAgentInResponse, "Mobile", "No", "Android");
                break;
            case "'Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1'":
                assertUserAgentDetails(userAgentInResponse, "Mobile", "Chrome", "iOS");
                break;
            case "'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)'":
                assertUserAgentDetails(userAgentInResponse, "Web", "No", "No");
                break;
            case "'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0'":
                assertUserAgentDetails(userAgentInResponse, "Web", "Chrome", "No");
                break;
            case "'Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1'":
                assertUserAgentDetails(userAgentInResponse, "Mobile", "No", "iPhone");
                break;
            default:
                break;
        }
    }

    private void assertUserAgentDetails(JsonPath userAgentInResponse, String platform, String browser, String device) {
        String useragent = userAgentInResponse.get("user_agent");
        String platformResponse = userAgentInResponse.get("platform");
        String browserResponse = userAgentInResponse.get("browser");
        String deviceResponse = userAgentInResponse.get("device");
        assertEquals(platform, platformResponse, "Wrong platform " + useragent);
        assertEquals(browser, browserResponse, "Wrong browser " + useragent);
        assertEquals(device, deviceResponse, "Wrong device " + useragent);
    }
}
