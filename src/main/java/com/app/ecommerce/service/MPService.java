/*
    Servicio encargado de recibir la info de Mp y devolverla al controlador y cualquier operacion que tenga que ver con MP
    Consultas, nuevos pagos, cancelaciones, notificaciones
 */
package com.app.ecommerce.service;

import com.app.ecommerce.entity.CardPayment;
import com.app.ecommerce.entity.PaymentResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Leona
 */
@Service
public class MPService {

    private PaymentResponse pr = new PaymentResponse();
    private ObjectMapper obMapper = new ObjectMapper();
    @Value("${spring.mp.conection.token}")
    private String token;
    @Value("${spring.mp.conection.post-url}")
    private String url;
    @Autowired
    private RestTemplate restTemplate;

    public PaymentResponse sendPostRequest(CardPayment cardPayment) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Authorization", "Bearer " + token);

            String bodyInfo = obMapper.writeValueAsString(cardPayment);
            System.out.println(bodyInfo);
            HttpEntity<CardPayment> requestEntity = new HttpEntity(bodyInfo, headers);
            ResponseEntity<PaymentResponse> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, PaymentResponse.class);

            pr.setStatusCode(response.getStatusCode());
            pr.setStatus(response.getBody().getStatus());
            pr.setDetail(response.getBody().getDetail());

        } catch (Exception e) {
            System.out.println(e.getClass() + " " + e.getMessage());
        }
        return pr;
    }

    public CardPayment findCardPayment(String id) {
        CardPayment cardPaymentResponse = new CardPayment();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<CardPayment> requestEntity = new HttpEntity(headers);
            System.out.println("url para post = " + url + "/" + id);
            ResponseEntity<CardPayment> response = restTemplate.exchange(url + "/" + id, HttpMethod.GET, requestEntity, CardPayment.class);
            System.out.println(response);
        } catch (Exception e) {
            System.out.println("Error en MPService = "+ e.getMessage() + " causa ="+ e.getCause());
        }
        return cardPaymentResponse;
    }
}
