package stepdefinitions;

import java.util.Map;

import org.junit.Assert;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import pages.EventAPI;
import pages.MerchantAPI;
import pages.PaymentSourceAPI;
import pages.TokenAPI;
import pages.TokenSubscriptionAPI;
import pages.TransactionAPI;

public class PayFlowSteps {
	private MerchantAPI merchantAPI;
	private TokenAPI tokenAPI;
	private Response response;
	private TokenSubscriptionAPI tokenSubscriptionAPI;
	private PaymentSourceAPI paymentSourceAPI;
	private TransactionAPI transactionAPI;
	private EventAPI eventAPI;

	public PayFlowSteps() {
		merchantAPI = new MerchantAPI();
		tokenAPI = new TokenAPI();
		tokenSubscriptionAPI = new TokenSubscriptionAPI();
		paymentSourceAPI = new PaymentSourceAPI();
		transactionAPI = new TransactionAPI();
		eventAPI = new EventAPI();
	}

	@Given("se crean los tokens de aceptación prefirmados")
	public void i_request_merchant_data() {
		merchantAPI.getMerchantInfo();
		Assert.assertEquals(200, merchantAPI.getStatusCode());
	}

	@When("se solicita la información del método de pago con el numero {string}")
	public void i_create_a_payment_method_with_phone_number(String phoneNumber) {
		response = tokenAPI.createPaymentMethod(phoneNumber);
	}

	@Then("se chequea el estado de la suscripción con un estado de {string}")
	public void the_response_response_status_is(String status) {
		Assert.assertEquals(status, tokenSubscriptionAPI.getStatusResponse(response));

	}

	@Then("se crea la fuente de pago para el usuario {string}")
	public void Create_a_payment_sources_from_NEQUI_to(String email) {
		response = paymentSourceAPI.createPaymentSource(email);
		Assert.assertEquals(201, paymentSourceAPI.getStatusCode(response));

	}

	@When("se crea la transaccion con la siguiente informacion:")
	public void i_create_a_transaction_with_the_following_details(io.cucumber.datatable.DataTable dataTable) {

		Map<String, String> data = dataTable.asMaps(String.class, String.class).get(0);

		int amountInCents = Integer.parseInt(data.get("amount_in_cents"));
		String currency = data.get("currency");
		String customerEmail = data.get("customer_email");
		String reference = data.get("reference");
		boolean isThreeDs = Boolean.parseBoolean(data.get("is_three_ds"));
		String threeDsAuthType = data.get("three_ds_auth_type");
		String fullName = data.get("full_name");
		String phoneNumber = data.get("phone_number");
		String legalId = data.get("legal_id");
		String legalIdType = data.get("legal_id_type");

		response = transactionAPI.createTransaction(amountInCents, currency, customerEmail, reference, isThreeDs,
				threeDsAuthType, fullName, phoneNumber, legalId, legalIdType);

		Assert.assertEquals(201, transactionAPI.getStatusCode(response));

	}

	@Then("se ejecuta un evento y se obtiene información sobre la transacción")
	public void i_send_the_transaction_event() {
		Assert.assertEquals(200, eventAPI.sendTransactionEvent(response));
	}

}
