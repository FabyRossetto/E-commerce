/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.ecommerce.controller;

import com.app.ecommerce.entity.CardPayment;
import com.app.ecommerce.entity.PaymentResponse;
import com.app.ecommerce.security.dto.Message;
import com.app.ecommerce.service.MPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Leona
 */
@RestController
@RequestMapping("/paymentCheckout") 
public class MPController {
    @Autowired
    private MPService servisMp;
    
    @GetMapping("")
    public ModelAndView devolverPayment(){
        return new ModelAndView("/checkOut/checkOut.html");
    }
    @PostMapping(value = "/sendRequest" ,consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PaymentResponse> sendRequestMP(@RequestBody CardPayment cardPayment){
        PaymentResponse pr = new PaymentResponse();
        try {
            pr = servisMp.sendPostRequest(cardPayment);
            System.out.println("Estado = " + pr.getStatusCode());
            System.out.println("Mensaje = " + pr.getDetail());
            return new ResponseEntity<PaymentResponse>(pr , HttpStatus.valueOf(pr.getStatusCode().value()));
        } catch (Exception e) {
            pr.setDetail(e.getMessage());
            return new ResponseEntity<PaymentResponse>(pr , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findPayment/{idPayment}")
    public ModelAndView findPaymentById(@PathVariable("idPayment") String idPayment ,
                                        Model model){
        CardPayment cardPayment = servisMp.findCardPayment(idPayment);
        model.addAttribute("CardPayment" , cardPayment);
        return new ModelAndView("/checkOut/checkOut.html");
    }

}
