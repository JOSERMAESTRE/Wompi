package pages;

import config.ApiConfig;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class TokenSubscriptionAPI {


    public String getStatusResponse(Response response) {

        String tokenId = TokenAPI.getTokenId();

        Response statusResponse = given()
                .baseUri(ApiConfig.getBaseUrl())
                .header("Authorization", "Bearer " + ApiConfig.getPublicKey())
                .when()
                .get("/tokens/nequi/" + tokenId)  
                .then()
                .extract()
                .response();

        return statusResponse.jsonPath().getString("data.status");  
    }
}
