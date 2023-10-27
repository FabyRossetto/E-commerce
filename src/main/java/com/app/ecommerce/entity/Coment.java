/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.ecommerce.entity;

import com.app.ecommerce.security.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

/**
 *
 * @author Leona
 */

@Entity
public class Coment {

    //----- Atributos ----
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int comentId;
    private Integer userId;
    private String coment;
    //----- Constructores ---
    public Coment() {
    }

    public Coment(Integer userId, String coment) {
        this.userId = userId;
        this.coment = coment;
    }

    public Coment(Integer userId, String coment, Integer comentId) {
        this.userId = userId;
        this.coment = coment;
    }
    


    // ---- G&S ----
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    @Override
    public String toString() {
        return "Coment{" +
                "userId=" + userId +
                ", coment='" + coment + '\'' +
                '}';
    }
}
