package steps;

import client.OrderClient;
import client.UserClient;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import model.Order;
import setup.UserGen;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    protected final UserGen gen = new UserGen();
    private final UserClient client = new UserClient();
    private final OrderClient orderClient = new OrderClient();
    private String accessToken;
    private final String apiOrder = "/api/orders";

    @Step("Create order with ingredients")
    public ValidatableResponse orderCreatedSuccessfullyWithIngredients(List<String> ingredients) {
        Order order = new Order(ingredients);
        ValidatableResponse response = orderClient.createOrder(order);
        return response;
    }

    @Step("order creation failed without ingredients")
    public ValidatableResponse orderCreationFailedWithIngredients() {
        Order order = new Order();
        order.setIngredients(null);
        ValidatableResponse response = orderClient.createOrder(order);
        return response;
    }

    @Step("order for authorized user create successfully")
    public ValidatableResponse orderForAuthorizedUserCreateSuccessfully(List<String> ingredients) {
        var user = gen.random();
        Order order = new Order(ingredients);
        accessToken = client.createWithToken(user);
        ValidatableResponse response = given()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .and().body(order)
                .when().post("/api/orders").then();
        return response;
    }

    @Step("Create order for user without authorization")
    public ValidatableResponse orderForNonAuthorizedUserCreatedSuccessfully(List<String> ingredients) {
        var user = gen.random();
        Order order = new Order(ingredients);
        accessToken = client.createWithToken(user);
        ValidatableResponse response = orderClient.createOrder(order);
        return response;
    }

    @Step("Create order with wrong ingredients")
    public ValidatableResponse orderNotCreatedWithWrongIngredients(List<String> ingredients) {
        Order order = new Order(ingredients);
        ValidatableResponse response = orderClient.createWrongIgreds(order);
        return response;
    }

    @Step("Return number of orders received")
    public ValidatableResponse numberOfOrdersReceivedSuccessfully() {
        var user = gen.random();
        accessToken = client.createWithToken(user);
        ValidatableResponse response = given()
                .auth().oauth2(accessToken.replace("Bearer ", ""))
                .get(apiOrder).then();
        return response;
    }

    @Step("Return number of orders not received without authorization")
    public ValidatableResponse numberOfOrdersNotReceivedWithoutAuthorization() {
        var user = gen.random();
        accessToken = client.createWithToken(user);
        ValidatableResponse response = given()
                .auth().oauth2(" ")
                .get(apiOrder).then();
        return response;
    }
}
