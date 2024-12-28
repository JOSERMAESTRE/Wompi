package pages;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

import config.ApiConfig;
import config.SHA256;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;


public class EventAPI {
	private SHA256 sHA256;

    public EventAPI() {
        this.sHA256 = new SHA256(); 
    }


	 public int sendTransactionEvent(Response transactionResponse) {
	        JSONObject responseJson = new JSONObject(transactionResponse.body().asString());
	        JSONObject dataJson = responseJson.getJSONObject("data");
	     
	        String transactionId = dataJson.getString("id");
	        int amountInCents = dataJson.getInt("amount_in_cents");
	        String reference = dataJson.getString("reference");
	        String customerEmail = dataJson.getString("customer_email");
	        String currency = dataJson.getString("currency");
	        String paymentMethodType = dataJson.getString("payment_method_type");
	        String status = dataJson.getString("status");
	        
	        String amountAsString = String.format("%d", amountInCents);
	    	
	       
	        JSONObject postData = new JSONObject();
	        postData.put("event", "transaction.updated");

	        JSONObject transactionData = new JSONObject();
	        transactionData.put("id", transactionId);
	        transactionData.put("amount_in_cents", amountInCents);
	        transactionData.put("reference", reference);
	        transactionData.put("customer_email", customerEmail);
	        transactionData.put("currency", currency);
	        transactionData.put("payment_method_type", paymentMethodType);
	        transactionData.put("status", status);
	        transactionData.put("shipping_address", JSONObject.NULL);
	        transactionData.put("payment_link_id", JSONObject.NULL);
	        transactionData.put("payment_source_id", JSONObject.NULL);

	        JSONObject dataObject = new JSONObject();
	        dataObject.put("transaction", transactionData);

	        postData.put("data", dataObject);
	        postData.put("environment", "test");

	        JSONObject signature = new JSONObject();
	        signature.put("properties", new String[]{"transaction.id", "transaction.status", "transaction.amount_in_cents"});
	      
	        postData.put("signature", signature);
	        postData.put("timestamp", System.currentTimeMillis() / 1000);
	        long timestamp = (System.currentTimeMillis() / 1000);
	        String checksum = sHA256.calculateSHA256(ApiConfig.getEventKey(), amountAsString, status, transactionId,String.valueOf(timestamp));
	        signature.put("checksum", checksum);
	        
	        ZonedDateTime now = ZonedDateTime.now(java.time.ZoneOffset.UTC);

	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
	        String formattedDate = now.format(formatter);

	        postData.put("sent_at", formattedDate);

	        Response response = given()
	                .baseUri(ApiConfig.getEventUrl())
	                .header("Content-Type", "application/json")
	                .body(postData.toString())
	                .post("/test/events")
	                .then()
	                .extract()
	                .response();

	      
	        return response.getStatusCode();
	    }
}
