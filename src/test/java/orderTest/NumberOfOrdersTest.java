package orderTest;

import client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import model.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import setup.UserGen;
import static io.restassured.RestAssured.given;

public class NumberOfOrdersTest {
    protected final UserGen gen = new UserGen();
    private final UserClient client = new UserClient();
    private final Assertions assertions = new Assertions();
    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }

    @Test
    @DisplayName("Check receive order for created user")
    public void numberOfOrdersReceivedSuccessfully() {
        var user = gen.random();
        accessToken = client.createWithToken(user);
        ValidatableResponse response = given()
                .auth().oauth2(accessToken.replace("Bearer ", ""))
                .get("/api/orders").then();
        assertions.successIsTrue(response);
    }
    @Test
    @DisplayName("Check receive order for created user without authorization")
    public void numberOfOrdersNotReceivedWithoutAuthorization() {
        var user = gen.random();
        accessToken = client.createWithToken(user);
        ValidatableResponse response = given()
                .auth().oauth2(" ")
                .get("/api/orders").then();
    }
    @After
    public  void deleteUser() {
        if (accessToken != null) {
            given().header("Authorization", accessToken)
                    .contentType(ContentType.JSON)
                    .and()
                    .delete("https://stellarburgers.nomoreparties.site/api/auth/user");
        }
    }
}
