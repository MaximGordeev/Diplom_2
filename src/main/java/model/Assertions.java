package model;

import io.restassured.response.ValidatableResponse;
import static org.hamcrest.Matchers.is;
import static org.apache.http.HttpStatus.*;

public class Assertions {
    public String successIsTrue(ValidatableResponse response) {
        return response.assertThat()
                .body("success", is(true))
                .and().statusCode(SC_OK)
                .extract().path("accessToken");
    }
    public String successIsFalseForbidden(ValidatableResponse response) {
        return response.assertThat()
                .body("success", is(false))
                .and().statusCode(SC_FORBIDDEN)
                .extract().path("accessToke");
    }
    public String successIsFalseUnauthorized(ValidatableResponse response) {
        return response.assertThat()
                .body("success", is(false))
                .and().statusCode(SC_UNAUTHORIZED)
                .extract().path("accessToken");
    }
    public String successIsFalseBadRequest(ValidatableResponse response) {
        return response.assertThat()
                .body("success", is(false))
                .and().statusCode(SC_BAD_REQUEST)
                .extract().path("accessToken");
    }
    public ValidatableResponse statusServerError(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}
