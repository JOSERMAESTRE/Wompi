Feature: Bancolombia QR
  como comercio
  quiero procesar pagos con QR bancolombia
  Para que los clientes puedan pagar mediante el QR de bancolombia

  Scenario Outline: proceso de pago con QR de bancolombia
    Given un cliente inicia un pago QR
    And el pago tiene los siguientes detalles:
      | campo               | valor                    |
      | payment_description | <payment_description>    |
      | sandbox_status      | <status>                 |
    When el código QR es generado
    And un código QR debería ser mostrado al cliente
    Then el estado de la transacción debería ser "<status>"
  
    Examples:
      | payment_description    | status    |
      | Pago Prueba QR         | APPROVED  |
      | Pago Rechazado QR      | DECLINED |
      | Pago Error QR          | ERROR    |

Feature: Botón de Transferencia Bancolombia  
  Como comerciante  
  Quiero procesar pagos con el botón de transferencia Bancolombia  
  Para que los clientes puedan realizar pagos usando este método  

   Scenario Outline: Procesar pagos con diferentes estados sandbox  
    Given que un cliente selecciona el método de pago Botón de Transferencia Bancolombia   
    When se procesa la transacción con la información proporcionada  
    Then el estado de la transacción debe ser "<status>"  
    And el enlace de pago asincrónico debe estar disponible en "<async_payment_url>"  

    Ejemplos:  
             | status    | async_payment_url              					 |  
  					 | APPROVED  | <<URL a cargar el paso de autenticación>> |  
     			   | DECLINED  | No disponible                   					 |  
     				 | ERROR     | No disponible                   					 |  
      