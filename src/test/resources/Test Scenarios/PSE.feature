Feature: Transferencia PSE
  Como comercio
  Quiero procesar transferencias PSE
  Para que los clientes puedan pagar desde su cuenta bancaria

  Scenario Outline: Procesar pagos con diferentes bancos
    Given el cliente selecciona PSE como método de pago
    And ingresa la siguiente información:
      | Campo                 | Valor                |
      | tipo_usuario         | <tipo_usuario>       |
      | tipo_documento       | <tipo_documento>     |
      | numero_documento     | <numero_documento>   |
      | codigo_banco         | <codigo_banco>       |
    And el tipo de método de pago debe ser "PSE"
    When se procesa la solicitud de pago
    Then el estado de la transacción debe ser "<estado>"
   

    Examples:
      | tipo_usuario | tipo_documento | numero_documento | codigo_banco | estado   |
      | 0          	 | CC             | 1999888777       | 1            | APPROVED |
      | 0            | CC             | 1999888777       | 2            | DECLINED |
      | 1          	 | NIT            | 9009009009       | 1            | APPROVED |