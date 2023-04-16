package steps;

import client.UserClient;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import model.Credentials;
import setup.UserGen;

import static io.restassured.RestAssured.given;

public class UserSteps {
    protected final UserGen gen = new UserGen();
    private final UserClient client = new UserClient();
    protected final utils.UserNewDataGenerator generatorMail = new utils.UserNewDataGenerator();
    private String accessToken;

    @Step("Create uniq user")
    public ValidatableResponse uniqUserCreatedSuccessfully() {
        var user = gen.random();
        ValidatableResponse response = client.create(user);
        return response;
    }
    @Step("User doesn`t have the Name (Song of Fire and Ice :) )")
    public ValidatableResponse createUserWithoutName() {
        var user = gen.random();
        user.setName(null);
        ValidatableResponse response = client.create(user);
        return response;
    }
    @Step("Creation fail without email")
    public ValidatableResponse creationFailsWithoutEmail() {
        var user = gen.random();
        user.setEmail(null);
        ValidatableResponse response = client.create(user);
        return response;
    }
    @Step("Creation fail without password")
    public ValidatableResponse creationFailsWithoutPassword() {
        var user = gen.random();
        user.setPassword(null);
        ValidatableResponse response = client.create(user);
        return response;
    }
    @Step("Try create existing user")
    public ValidatableResponse existingUserCreationFails() {
        var user = gen.random();
        ValidatableResponse response = client.create(user);
        ValidatableResponse response11 = client.create(user);
        return response11;
    }
    @Step("Update user data")
    public ValidatableResponse userDataUpdatedSuccessfully() {
        var user = gen.random();
        accessToken = client.createWithToken(user);
        var userNewData = generatorMail.random();
        ValidatableResponse response = given()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .and()
                .body(userNewData)
                .when()
                .patch("/api/auth/user").then();
        return response;
    }
    @Step("User data update failed without authorization")
    public ValidatableResponse userDataUpdateFailedWithoutAuthorization() {
        var user = gen.random();
        client.create(user);
        var userNewData = generatorMail.random();
        ValidatableResponse response = client.updateData(userNewData);
        return response;
    }
    @Step("User logged in successfully")
    public ValidatableResponse userLoggedInSuccessfully() {
        var user = gen.random();
        client.create(user);
        Credentials creds = Credentials.from(user);
        ValidatableResponse response = client.loginWithCreds(creds);
        return response;
    }
    @Step("User try log in with fake email")
    public ValidatableResponse userWithFakeEmailLoggedInFailed() {
        var user = gen.random();
        client.create(user);
        user.setEmail("diplomzdavaysya@gmail.com");
        user.setPassword("1234");
        ValidatableResponse response = client.login(user);
        return response;
    }
}
