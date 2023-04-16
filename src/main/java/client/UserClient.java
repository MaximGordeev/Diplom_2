package client;

import com.fasterxml.jackson.databind.ser.impl.UnknownSerializer;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

import model.Credentials;
import model.NewUserData;
import model.User;

public class UserClient {
    private final String apiRegister = "/api/auth/register";
    private final String apiUser = "/api/auth/user";
    private final String apiLogin = "/api/auth/login";
    private String accessToken;

    @Step("Create user")
    public ValidatableResponse create(User user) {
        return given()
                .contentType(ContentType.JSON)
                .and().body(user)
                .when().post(apiRegister)
                .then();
    }
    @Step("Login user")
    public ValidatableResponse login(User user) {
        return given()
                .contentType(ContentType.JSON)
                .and().body(user)
                .when().post(apiLogin)
                .then();
    }
    @Step("Login with credentials")
    public ValidatableResponse loginWithCreds(Credentials creds) {
        return given()
                .contentType(ContentType.JSON)
                .and().body(creds)
                .when().post(apiLogin)
                .then();
    }
    @Step("Update user data")
    public ValidatableResponse updateData(NewUserData newUserData) {
        return given()
                .contentType(ContentType.JSON)
                .and().body(newUserData)
                .when().patch(apiUser)
                .then();
    }
    @Step("Create user with credentials")
    public ValidatableResponse createWithCreds(Credentials creds) {
        return given()
                .contentType(ContentType.JSON)
                .and().body(creds)
                .when().post(apiLogin)
                .then();
    }
    @Step("Create user with token")
    public String createWithToken(User user) {
        return given()
                .contentType(ContentType.JSON)
                .and().body(user)
                .when().post(apiRegister)
                .then().statusCode(SC_OK)
                .extract().path("accessToken");
    }
}
