package userTest;

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

public class CreateUserTest {
    protected final UserGen gen = new UserGen();
    private final UserClient client = new UserClient();
    private final Assertions assertions = new Assertions();
    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }
    @Test
    @DisplayName("Create uniq user")
    public void uniqUserCreatedSuccessfully() {
        var user = gen.random();
        ValidatableResponse response = client.create(user);
        accessToken = assertions.successIsTrue(response);
    }
    @Test
    @DisplayName("Created user without name")
    public void createUserWithoutName() {
        var user = gen.random();
        user.setName(null);
        ValidatableResponse response = client.create(user);
        accessToken = assertions.successIsFalseForbidden(response);
    }
    @Test
    @DisplayName("Created user without email")
    public void creationFailsWithoutEmail() {
        var user = gen.random();
        user.setEmail(null);
        ValidatableResponse response = client.create(user);
        accessToken = assertions.successIsFalseForbidden(response);
    }

    @Test
    @DisplayName("Created user without password")
    public void creationFailsWithoutPassword() {
        var user = gen.random();
        user.setPassword(null);
        ValidatableResponse response = client.create(user);
        accessToken = assertions.successIsFalseForbidden(response);
    }

    @Test
    @DisplayName("Create existing user")
    public void existingUserCreationFails() {
        var user = gen.random();
        ValidatableResponse response = client.create(user);
        ValidatableResponse response11 = client.create(user);
        accessToken = assertions.successIsFalseForbidden(response11);
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
