package pages;

import config.ApiConfig;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class PaymentSourceAPI {

	private static int PaymentID;
    public Response createPaymentSource(String customerEmail) {
    	  String tokenId = TokenAPI.getTokenId();
    	  String acceptanceToken = MerchantAPI.getAcceptanceToken();
    	  String acceptPersonalAuth = MerchantAPI.getaccept_personal_authToken();
    	  String jsonBody = "{\n" +
                "  \"type\": \"NEQUI\",\n" +
                "  \"token\": \"" + tokenId + "\",\n" +
                "  \"customer_email\": \"" + customerEmail + "\",\n" +
                "  \"acceptance_token\": \"" + acceptanceToken + "\",\n" +
                "  \"accept_personal_auth\": \"" + acceptPersonalAuth + "\"\n" +
                "}";
        Response response = given()
                .baseUri(ApiConfig.getBaseUrl()) 
                .header("Authorization", "Bearer " + ApiConfig.getPrivateKey())
                .body(jsonBody) 
                .when()
                .post("/payment_sources") 
                .then()
                .extract()
                .response();
        SetPaymentID(response);
        return response;
    }
    
    private void SetPaymentID(Response response) {
    	PaymentID = response.jsonPath().getInt("data.id");
    }

    public static int getPaymentID() {
        return PaymentID;
    }
    
    public int getStatusCode(Response response) {
        return response.getStatusCode();
    }

    public String getResponseBody(Response response) {
        return response.body().asString();
    }
}
