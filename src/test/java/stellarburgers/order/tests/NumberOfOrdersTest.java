package stellarburgers.order.tests;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import model.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import steps.OrderSteps;

import static io.restassured.RestAssured.given;

public class NumberOfOrdersTest {
    private final Assertions assertions = new Assertions();
    private final OrderSteps orderSteps = new OrderSteps();
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
    @DisplayName("Check receive order for created user")
    public void checkNumberOfOrdersReceivedSuccessfully() {
        assertions.successIsTrue(orderSteps.numberOfOrdersReceivedSuccessfully());
    }
    @Test
    @DisplayName("Check receive order for created user without authorization")
    public void checkNumberOfOrdersNotReceivedWithoutAuthorization() {
        assertions.successIsFalseUnauthorized(orderSteps.numberOfOrdersNotReceivedWithoutAuthorization());
    }
    @After
    @Step("Delete user")
    public  void deleteUser() {
        if (accessToken != null) {
            given().header("Authorization", accessToken)
                    .contentType(ContentType.JSON)
                    .and()
                    .delete("https://stellarburgers.nomoreparties.site/api/auth/user");
        }
    }
}
