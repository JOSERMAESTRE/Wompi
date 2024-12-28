package stepdefinitions;


import io.cucumber.datatable.DataTable;

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

	@Given("se crean los tokens de aceptacion prefirmados")
	public void se_crean_los_tokens_de_aceptacion_prefirmados() {
		merchantAPI.getMerchantInfo();
		Assert.assertEquals(200, merchantAPI.getStatusCode());
	}

	@When("se solicita la informacion del metodo de pago con el numero {string}")
	public void se_solicita_la_informacion_del_metodo_de_pago_con_el_numero(String phoneNumber) {
		response = tokenAPI.createPaymentMethod(phoneNumber);
	}

	@Then("se chequea el estado de la suscripcion con un estado de {string}")
	public void se_chequea_el_estado_de_la_suscripcion_con_un_estado_de(String status) {
		Assert.assertEquals(status, tokenSubscriptionAPI.getStatusResponse(response));

	}

	@Then("se crea la fuente de pago para el usuario {string}")
	public void se_crea_la_fuente_de_pago_para_el_usuario(String email) {
		response = paymentSourceAPI.createPaymentSource(email);
		Assert.assertEquals(201, paymentSourceAPI.getStatusCode(response));

	}

	@When("se crea la transaccion con la siguiente informacion")
	public void se_crea_la_transaccion_con_la_siguiente_informacion(DataTable dataTable) {

		response = transactionAPI.createTransaction(dataTable);
		Assert.assertEquals(201, transactionAPI.getStatusCode(response));

	}

	@Then("se ejecuta un evento y se obtiene informacion sobre la transaccion")
	public void se_ejecuta_un_evento_y_se_obtiene_informacion_sobre_la_transaccion() {
		Assert.assertEquals(200, eventAPI.sendTransactionEvent(response));
	}

}
