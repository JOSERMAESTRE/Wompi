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
	public void se_crean_los_tokens_de_aceptación_prefirmados() {
		merchantAPI.getMerchantInfo();
		Assert.assertEquals(200, merchantAPI.getStatusCode());
	}

	@When("se solicita la información del método de pago con el numero {string}")
	public void se_solicita_la_información_del_método_de_pago_con_el_numero(String phoneNumber) {
		response = tokenAPI.createPaymentMethod(phoneNumber);
	}

	@Then("se chequea el estado de la suscripción con un estado de {string}")
	public void se_chequea_el_estado_de_la_suscripción_con_un_estado_de(String status) {
		Assert.assertEquals(status, tokenSubscriptionAPI.getStatusResponse(response));

	}

	@Then("se crea la fuente de pago para el usuario {string}")
	public void Create_a_payment_sources_from_NEQUI_to(String email) {
		response = paymentSourceAPI.createPaymentSource(email);
		Assert.assertEquals(201, paymentSourceAPI.getStatusCode(response));

	}

	@When("se crea la transaccion con la siguiente informacion:")
	public void se_crea_la_transaccion_con_la_siguiente_informacion(io.cucumber.datatable.DataTable dataTable) {

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
	public void se_ejecuta_un_evento_y_se_obtiene_información_sobre_la_transacción() {
		Assert.assertEquals(200, eventAPI.sendTransactionEvent(response));
	}

}
