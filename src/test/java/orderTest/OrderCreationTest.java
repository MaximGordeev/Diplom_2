package orderTest;

import client.OrderClient;
import client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import model.Assertions;
import model.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import setup.UserGen;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

public class OrderCreationTest {
    protected final UserGen gen = new UserGen();
    private final UserClient client = new UserClient();
    private final OrderClient orderClient = new OrderClient();
    private final Assertions assertions = new Assertions();
    List<String> ingredients = new ArrayList<>();
    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        ingredients.add("61c0c5a71d1f82001bdaaa6d");
        ingredients.add("61c0c5a71d1f82001bdaaa72");
    }
    @Test
    @DisplayName("Create order with ingredients")
    public void orderCreatedSuccessfullyWithIngredients() {
        Order order = new Order(ingredients);
        ValidatableResponse response = orderClient.createOrder(order);
        assertions.successIsTrue(response);
    }
    @Test
    @DisplayName("Create order without ingredients")
    public void orderCreationFailedWithjutIngredients() {
        Order order = new Order(ingredients);
        order.setIngredients(null);
        ValidatableResponse response = orderClient.createOrder(order);
        assertions.successIsFalseBadRequest(response);
    }
    @Test
    @DisplayName("Create order for authorized user")
    public void orderForAuthorizedUserCreateSuccessfully() {
        var user = gen.random();
        Order order = new Order(ingredients);
        accessToken = client.createWithToken(user);
        ValidatableResponse response = given().log().all()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .and().body(order)
                .when().post("/api/orders").then();
        assertions.successIsTrue(response);
    }
    @Test
    @DisplayName("Create order for user without authorization")
    public void orderForNonAuthorizedUserCreatedSuccessfully() {
        var user = gen.random();
        Order order = new Order(ingredients);
        accessToken = client.createWithToken(user);
        ValidatableResponse response = orderClient.createOrder(order);
        assertions.successIsTrue(response);
    }
    @Test
    @DisplayName("Create order with wrong ingredients")
    public void orderNotCreatedWithWrongIngredients() {
        Order order = new Order(ingredients);
        ValidatableResponse response = orderClient.createWrongIgreds(order);
        assertions.statusServerError(response);
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
