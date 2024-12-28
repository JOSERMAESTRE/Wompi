Feature: Prueba de integracion Wompi
	Como integrador de Wompi
  Quiero probar la funcionalidad de transacciones con Nequi
  Para asegurar que los flujos funcionan correctamente en el sistema
  Scenario: Transaccion con cuenta nequi
    Given se crean los tokens de aceptacion prefirmados
    When se solicita la informacion del metodo de pago con el numero "3194560279"
    Then se chequea el estado de la suscripcion con un estado de "APPROVED"
    Then se crea la fuente de pago para el usuario "jose.rafa@example.com"
    When se crea la transaccion con la siguiente informacion
      | amount_in_cents | currency |customer_email           | reference  | is_three_ds | three_ds_auth_type | full_name    | phone_number | legal_id    | legal_id_type | 
      | 2500000         | COP      | jose.rafa@example.com   | 54937     	|  true       | challenge       | jose maestre | 3194560279   | 1065658149  | CC            |
    Then se ejecuta un evento y se obtiene informacion sobre la transaccion
       