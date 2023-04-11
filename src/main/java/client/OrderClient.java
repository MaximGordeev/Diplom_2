package client;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
import model.Order;

public class OrderClient {
    public ValidatableResponse createOrder(Order order) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and().body(order)
                .when().post("/api/orders").then();
    }
    public ValidatableResponse createWrongIgreds(Order order) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and().body("{\"ingredients\": \"bambucha\"}")
                .when().post("/api/orders").then();
    }
}
