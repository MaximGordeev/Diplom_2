package stellarburgers.order.tests;

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
import steps.OrderSteps;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

public class OrderCreationTest {
    private final OrderSteps orderSteps = new OrderSteps();
    private final Assertions assertions = new Assertions();
    List<String> ingredients = new ArrayList<>();
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
        ingredients.add("61c0c5a71d1f82001bdaaa6d");
        ingredients.add("61c0c5a71d1f82001bdaaa72");
    }
    @Test
    @DisplayName("Check create order with ingredients")
    public void checkOrderCreatedSuccessfullyWithIngredients() {
        assertions.successIsTrue(orderSteps.orderCreatedSuccessfullyWithIngredients(ingredients));
    }
    @Test
    @DisplayName("Check create order without ingredients")
    public void checkOrderCreationFailedWithIngredients() {
        assertions.successIsFalseBadRequest(orderSteps.orderCreationFailedWithIngredients());
    }
    @Test
    @DisplayName("Check create order for authorized user")
    public void checkOrderForAuthorizedUserCreateSuccessfully() {
        assertions.successIsTrue(orderSteps.orderForAuthorizedUserCreateSuccessfully(ingredients));
    }
    @Test
    @DisplayName("Check create order for user without authorization")
    public void checkOrderForNonAuthorizedUserCreatedSuccessfully() {
        assertions.successIsTrue(orderSteps.orderForNonAuthorizedUserCreatedSuccessfully(ingredients));
    }
    @Test
    @DisplayName("Check create order with wrong ingredients")
    public void checkOrderNotCreatedWithWrongIngredients() {
        assertions.statusServerError(orderSteps.orderNotCreatedWithWrongIngredients(ingredients));
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
