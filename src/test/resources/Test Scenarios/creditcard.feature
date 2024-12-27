Feature: Pagos con Tarjeta de Crédito
  Como comercio
  Quiero procesar pagos con tarjeta
  Para recibir pagos de mis clientes

  Background:
    Given el comercio ha integrado el sistema de pago Wompi

  Scenario Outline: Procesar pago con tarjetas debito y credito
    Given el cliente inicia un pago con tarjeta por "<monto>" COP
    And el tipo de método de pago debe ser "CARD"
    And ingresa el número de tarjeta "<numero_tarjeta>"
    And ingresa una fecha de vencimiento de la tarjeta
    And ingresa un CVC válido de 3 dígitos
    When se procesa el pago
    Then el estado de la transacción debe ser "<estado>"

    Examples:
      | numero_tarjeta     | monto   | estado    |
      | 4242424242424242   | 50000   | APPROVED  |
      | 4111111111111111   | 50000   | DECLINED  |
      | 5555555555554444   | 50000   | ERROR     |