package userTest;

import client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import model.Assertions;
import model.Credentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import setup.UserGen;

import static io.restassured.RestAssured.given;

public class UserLoginTest {
    protected final UserGen generator = new UserGen();

    private final UserClient client = new UserClient();
    private final Assertions assertions = new Assertions();
    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }

    @Test
    @DisplayName("User authorization")
    public void userLoggedInSuccessfully() {
        var user = generator.random();
        client.create(user);
        Credentials creds = Credentials.from(user);
        ValidatableResponse response = client.loginWithCreds(creds);
        accessToken = assertions.successIsTrue(response);
    }

    @Test
    @DisplayName("User authorization with incorrect email")
    public void userWithFakeEmailLoggedInFailed() {
        var user = generator.random();
        client.create(user);
        user.setEmail("diplomzdavaysya@gmail.com");
        user.setPassword("1234");
        ValidatableResponse response = client.login(user);
        accessToken = assertions.successIsFalseUnauthorized(response);
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            given().header("Authorization", accessToken)
                    .contentType(ContentType.JSON)
                    .and()
                    .delete("https://stellarburgers.nomoreparties.site/api/auth/user");
        }
    }
}