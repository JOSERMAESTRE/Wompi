Feature: Pagos con Nequi
  Como comercio
  Quiero procesar pagos con Nequi
  Para que los clientes puedan pagar usando su cuenta Nequi

  Background:
    Given el comercio ha configurado Nequi como método de pago

  Scenario Outline: Procesar pagos con cuentas nequi
    Given el cliente selecciona Nequi como método de pago
    And ingresa el número de teléfono "<numero_telefono>"
    And el tipo de método de pago debe ser "NEQUI" 
    When se envía la solicitud de pago
    Then el estado de la transacción debe ser "<estado>"
    And se debe enviar una notificación al teléfono del cliente
   
    Examples:
      | numero_telefono | estado   	|
      | 3991111111     	| APPROVED  |
      | 3992222222     	| DECLINED  |
      | 3999999999     	| ERROR     |