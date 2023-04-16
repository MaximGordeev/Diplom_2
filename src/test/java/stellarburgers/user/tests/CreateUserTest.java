package stellarburgers.user.tests;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import model.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import steps.UserSteps;

import static io.restassured.RestAssured.given;

public class CreateUserTest {
    private final Assertions assertions = new Assertions();
    private final UserSteps userSteps = new UserSteps();
    private String accessToken;

    @BeforeClass
    public static void globalSetup() {
        RestAssured.filters(
                new RequestLoggingFilter(), new ResponseLoggingFilter(),
                new AllureRestAssured()
        );
    }
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }
    @Test
    @DisplayName("Check create uniq user")
    public void checkUserCreatedSuccessfully() {
        accessToken = assertions.successIsTrue(userSteps.uniqUserCreatedSuccessfully());
    }
    @Test
    @DisplayName("Check created user without name")
    public void checkCreateUserWithoutName() {
        accessToken = assertions.successIsFalseForbidden(userSteps.createUserWithoutName());
    }
    @Test
    @DisplayName("Check created user without email")
    public void creationFailsWithoutEmail() {
        accessToken = assertions.successIsFalseForbidden(userSteps.creationFailsWithoutEmail());
    }

    @Test
    @DisplayName("Check create user without password")
    public void checkCreationFailsWithoutPassword() {
        accessToken = assertions.successIsFalseForbidden(userSteps.creationFailsWithoutPassword());
    }

    @Test
    @DisplayName("Check create existing user")
    public void checkExistingUserCreationFails() {
        accessToken = assertions.successIsFalseForbidden(userSteps.existingUserCreationFails());
    }

    @After
    @Step("Delete user")
    public void deleteUser() {
        if (accessToken != null) {
            given().header("Authorization", accessToken)
                    .contentType(ContentType.JSON)
                    .and()
                    .delete("https://stellarburgers.nomoreparties.site/api/auth/user");
        }
    }
}
