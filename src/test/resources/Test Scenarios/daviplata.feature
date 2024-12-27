Feature: Pagos Simples con DaviPlata
  Como comercio
  Quiero procesar pagos con DaviPlata
  Para que los clientes puedan pagar usando su cuenta DaviPlata

  Background:
    Given el comercio ha configurado DaviPlata como método de pago

  Scenario Outline: Procesar pagos con diferentes códigos de verificación
    Given el cliente selecciona DaviPlata como método de pago
    And ingresa un número de teléfono válido
    When el cliente recibe e ingresa el código de verificación "<codigo>"
    Then el estado de la transacción debe ser "<estado>"
    And el tipo de método de pago debe ser "DAVIPLATA"
    And el sistema debe mostrar "<mensaje>"

    Examples:
      | codigo  | estado    | mensaje                       |
      | 574829  | APPROVED  | Transacción exitosa           |
      | 932015  | DECLINED  | Transacción rechazada         |
      | 186743  | DECLINED  | Saldo insuficiente            |
      | 999999  | ERROR     | Error en el sistema           |
 
Feature: Pagos Recurrentes con DaviPlata
  Como comercio
  Quiero configurar pagos recurrentes con DaviPlata
  Para que los clientes puedan realizar pagos automáticos

  Background:
    Given el comercio ha configurado pagos recurrentes

  Scenario Outline: Tokenizar cuenta daviplata
    Given el cliente quiere configurar pagos recurrentes
    And ingresa el número de teléfono "<numero_telefono>"
    When el cliente solicita la creación del token
    And ingresa el código de verificación "<codigo>"
    Then el estado del token debe ser "<estado>"
    And el sistema debe mostrar "<mensaje>"

    Examples:
      | numero_telefono | codigo  | estado   | mensaje                          |
      | 3991111111     | 574829  | APPROVED  | Token creado exitosamente        |
      | 3992222222     | *       | DECLINED  | Creación de token rechazada      |
      | 3993333333     | *       | DECLINED  | Billetera inválida               |
      | 3991111111     | 932016  | DECLINED  | Suscripción ya existe            |
      | 3991111111     | 123456  | ERROR     | Código inválido                  |
      