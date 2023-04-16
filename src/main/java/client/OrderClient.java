package client;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
import model.Order;

public class OrderClient {
    private final String apiOrder = "/api/orders";
    @Step("Create order")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .contentType(ContentType.JSON)
                .and().body(order)
                .when().post(apiOrder).then();
    }
    @Step("Create wrong ingredients")
    public ValidatableResponse createWrongIgreds(Order order) {
        return given()
                .contentType(ContentType.JSON)
                .and().body("{\"ingredients\": \"bambucha\"}")
                .when().post(apiOrder).then();
    }
}
