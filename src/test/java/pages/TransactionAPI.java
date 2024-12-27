package pages;

import config.ApiConfig;
import config.SHA256;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class TransactionAPI {
	
		private SHA256 sHA256;

	    public TransactionAPI() {
	        this.sHA256 = new SHA256(); 
	    }
    public Response createTransaction(int amountInCents, String currency, String customerEmail, String reference,  boolean isThreeDs, String threeDsAuthType, String fullName, String phoneNumber, String legalId, String legalIdType) {
    	int paymentSourceId = PaymentSourceAPI.getPaymentID();
    	String amountAsString = String.format("%d", amountInCents);
    	String timestamp ="";
    	String signature = sHA256.calculateSHA256(ApiConfig.getIntegrityKey(), currency, amountAsString, reference,timestamp);
        String jsonBody = "{\n" +
                "  \"amount_in_cents\": " + amountInCents + ",\n" +
                "  \"currency\": \"" + currency + "\",\n" +
                "  \"signature\": \"" + signature + "\",\n" +
                "  \"customer_email\": \"" + customerEmail + "\",\n" +
                "  \"reference\": \"" + reference + "\",\n" +
                "  \"payment_source_id\": " + paymentSourceId + ",\n" +
                "  \"is_three_ds\": " + isThreeDs + ",\n" +
                "  \"three_ds_auth_type\": \"" + threeDsAuthType + "\",\n" +
                "  \"customer_data\": {\n" +
                "    \"full_name\": \"" + fullName + "\",\n" +
                "    \"phone_number\": \"" + phoneNumber + "\",\n" +
                "    \"legal_id\": \"" + legalId + "\",\n" +
                "    \"legal_id_type\": \"" + legalIdType + "\"\n" +
                "  }\n" +
                "}";

        Response response = given()
                .baseUri(ApiConfig.getBaseUrl())  
                .header("Authorization", "Bearer " + ApiConfig.getPrivateKey())  
                .body(jsonBody) 
                .when()
                .post("/transactions/")  
                .then()
                .extract()
                .response();  

        return response;
    }

    public int getStatusCode(Response response) {
        return response.getStatusCode();
    }

    public String getResponseBody(Response response) {
        return response.body().asString();
    }
}
