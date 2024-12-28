package pages;

import config.ApiConfig;
import config.SHA256;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import java.util.Map;

public class TransactionAPI {
	
		private SHA256 sHA256;

	    public TransactionAPI() {
	        this.sHA256 = new SHA256(); 
	    }
    public Response createTransaction(DataTable dataTable) {
    	Map<String, String> data = dataTable.asMaps(String.class, String.class).get(0);

        int amountInCents = Integer.parseInt(data.get("amount_in_cents"));
        int paymentSourceId = PaymentSourceAPI.getPaymentID();
        String amountAsString = String.valueOf(Integer.parseInt(data.get("amount_in_cents")));
        String timestamp = "";
        String signature = sHA256.calculateSHA256(
            ApiConfig.getIntegrityKey(), data.get("currency"), amountAsString, data.get("reference"), timestamp);

        String jsonBody = "{\n" +
            "  \"amount_in_cents\": " + amountInCents + ",\n" +
            "  \"currency\": \"" + data.get("currency") + "\",\n" +
            "  \"signature\": \"" + signature + "\",\n" +
            "  \"customer_email\": \"" + data.get("customer_email") + "\",\n" +
            "  \"reference\": \"" + data.get("reference") + "\",\n" +
            "  \"payment_source_id\": " + paymentSourceId + ",\n" +
            "  \"is_three_ds\": " + Boolean.parseBoolean(data.get("is_three_ds")) + ",\n" +
            "  \"three_ds_auth_type\": \"" + data.get("three_ds_auth_type") + "\",\n" +
            "  \"customer_data\": {\n" +
            "    \"full_name\": \"" + data.get("full_name") + "\",\n" +
            "    \"phone_number\": \"" + data.get("legal_id") + "\",\n" +
            "    \"legal_id\": \"" + data.get("legal_id") + "\",\n" +
            "    \"legal_id_type\": \"" + data.get("legal_id_type") + "\"\n" +
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
