/*
    Informacion del pagador (Email importantisimo) + DNI Y TIPO (payerIdentity)
    Estos datos podriamos guardarlos en la BBDD, asociandolo a USER?
 */
package com.app.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Leona
 */
public class Payer {
    //--------- Atributos ---------
    @JsonProperty("email")
    private String email;
    @JsonProperty("identification")
    private PayerIdentity payerIden;
    //------- Constructor -------------

    public Payer() {
    }

    public Payer(String email, PayerIdentity payerIden) {
        this.email = email;
        this.payerIden = payerIden;
    }
    //----- G&S -----

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PayerIdentity getPayerIden() {
        return payerIden;
    }

    public void setPayerIden(PayerIdentity payerIden) {
        this.payerIden = payerIden;
    }
    
}
    