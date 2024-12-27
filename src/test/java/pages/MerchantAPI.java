package pages;

import config.ApiConfig;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class MerchantAPI {
	private Response response;
	private static String acceptanceToken;
	private static String accept_personal_authToken;

	public void getMerchantInfo() {
		response = given()
				.baseUri(ApiConfig.getBaseUrl())
				.header("Authorization", "Bearer " + ApiConfig.getPublicKey())
				.when().get("/merchants/" + ApiConfig.getPublicKey())
				.then()
				.extract()
				.response();
		acceptanceToken = response.jsonPath().getString("data.presigned_acceptance.acceptance_token");
		accept_personal_authToken = response.jsonPath().getString("data.presigned_personal_data_auth.acceptance_token");
	}

	public int getStatusCode() {
		return response.getStatusCode();
	}

	public String getResponseBody() {
		return response.body().prettyPrint();
	}

	public static String getAcceptanceToken() {
		return acceptanceToken;
	}
	public static String getaccept_personal_authToken() {
		return accept_personal_authToken;
	}

}
