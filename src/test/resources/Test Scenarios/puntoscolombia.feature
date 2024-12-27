Feature: Puntos Colombia
  Como comerciante
  Quiero procesar pagos con Puntos Colombia
  Para que los clientes puedan pagar usando sus puntos

   Scenario Outline: Procesar pagos con diferentes combinaciones de puntos
    Given que un cliente selecciona el método de pago Puntos Colombia
   	And el pago tiene un estado sandbox "<sandbox_status>"
    When se procesa el pago
    Then el estado de la transacción debe ser "<status>"
    And el uso de puntos debe ser "<points_usage>"

    Examples:
      | sandbox_status       | status    | points_usage       |
      | APPROVED_ONLY_POINTS | APPROVED  | 100% puntos        |
      | APPROVED_HALF_POINTS | APPROVED  | 50% puntos         |
      | DECLINED             | DECLINED  | Sin uso de puntos  |
      | ERROR                | ERROR     | Sin uso de puntos  |
