/*
    Entidad encargada de recibir los datos del front
    y armar la info que se va a enviar a MP
 */
package com.app.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CardPayment {
    //---- Atributos -------
    @JsonProperty("token")
    private String token;
    @JsonProperty("issuer_id")
    private String issuerId;
    @JsonProperty("payment_method_id")
    private String paymentMethodId;
    @JsonProperty("transaction_amount")
    private BigDecimal transactionAmount;
    @JsonProperty("installments")
    private Integer installemnts;//cuotas
    @JsonProperty("description")
    private String productDescription;
    @JsonProperty("payer")
    private Payer payer;//Identidad del que paga (no tiene que ser null)
    
    
    //----- Constructores -----

    public CardPayment() {
    }

    public CardPayment(String token, String issuerId, String paymentMethodId, BigDecimal transactionAmount, Integer installemnts, String productDescription) {
        this.token = token;
        this.issuerId = issuerId;
        this.paymentMethodId = paymentMethodId;
        this.transactionAmount = transactionAmount;
        this.installemnts = installemnts;
        this.productDescription = productDescription;
    }
    
    //------- G&S --------

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Integer getInstallemnts() {
        return installemnts;
    }

    public void setInstallemnts(Integer installemnts) {
        this.installemnts = installemnts;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Payer getPayer() {
        return payer;
    }

    public void setPayer(Payer payer) {
        this.payer = payer;
    }
    
}
