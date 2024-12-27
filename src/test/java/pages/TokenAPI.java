package pages;

import config.ApiConfig;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class TokenAPI {
    

    private static String tokenID;
    
    public Response createPaymentMethod(String phoneNumber) {
        String jsonBody = "{\n" + "    \"phone_number\": \"" + phoneNumber + "\"\n" + "}";

        Response response = given()
                .baseUri(ApiConfig.getBaseUrl())
                .header("Authorization", "Bearer " + ApiConfig.getPublicKey())
                .body(jsonBody)
                .when().post("/tokens/nequi/")
                .then().extract().response();
        setTokenId(response);

        return response; 
    }

    private void setTokenId(Response response) {
        tokenID = response.jsonPath().getString("data.id");
    }

    public static String getTokenId() {
        return tokenID;
    }


    public int getStatusCode(Response response) {
        return response.getStatusCode();
    }


    public String getResponseBody(Response response) {
        return response.getBody().asString();
    }
}
