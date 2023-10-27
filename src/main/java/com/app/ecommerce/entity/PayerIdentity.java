/*
  "Identidad" del pagador (Tipo de Id + Numero ) = DNI 425184454
 */
package com.app.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Leona
 */
public class PayerIdentity {
    //---- Atributos -----
    @JsonProperty("type")
    private String type;
    @JsonProperty("number")
    private String identificationNumber;
    //--- Constructores -----
    public PayerIdentity() {
    }

    public PayerIdentity(String type, String identificationNumber) {
        this.type = type;
        this.identificationNumber = identificationNumber;
    }
    //------- G&S ----------

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }
    
}
