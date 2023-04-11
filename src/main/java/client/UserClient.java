package client;

import com.fasterxml.jackson.databind.ser.impl.UnknownSerializer;
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

    public ValidatableResponse create(User user) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and().body(user)
                .when().post(apiRegister)
                .then().log().all();
    }
    public ValidatableResponse login(User user) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and().body(user)
                .when().post(apiLogin)
                .then().log().all();
    }
    public ValidatableResponse loginWithCreds(Credentials creds) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and().body(creds)
                .when().post(apiLogin)
                .then().log().all();
    }
    public ValidatableResponse updateData(NewUserData newUserData) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and().body(newUserData)
                .when().patch(apiUser)
                .then().log().all();
    }
    public ValidatableResponse createWithCreds(Credentials creds) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and().body(creds)
                .when().post(apiLogin)
                .then().log().all();
    }
    public String createWithToken(User user) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and().body(user)
                .when().post(apiRegister)
                .then().statusCode(SC_OK)
                .extract().path("accessToken");
    }
}
