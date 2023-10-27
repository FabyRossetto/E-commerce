/*
    Entidad encargada de recibir los datos de MP una vez enviada la peticion a sus servidores
    No quiero guardar esta informacion en BBDD ya que es extensa, y hay que guardar el ID de la transaccion
    Para despues enviarla a mp si hay que corroborar algun dato.
    ¿O si conviene almacenar esta entidad y armar un atributo para relacionarlo con algun cliente?
 */
package com.app.ecommerce.entity;

import org.springframework.http.HttpStatusCode;

/**
 *
 * @author Leona
 */
public class PaymentResponse {
    //----- Atributos -----
    private Long id;//Este ID se puede almacenar en ORDER
    private String status;//Estado ("APROBADO" / "PENDIENTE" / "RECHASADO")
    private String detail;//Detalle de la operacion
    private HttpStatusCode statusCode;
    // --- Constructores ----

    public PaymentResponse() {
    }
    public PaymentResponse(Long id, String status, String detail) {
        this.id = id;
        this.status = status;
        this.detail = detail;
    }
    
    //--------- Getters y Setters --------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
    }


}
