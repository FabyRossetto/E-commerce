/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.ecommerce.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Leona
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class LatLong {
    //--Atributos--
    private long latitude;
    private long longitude;


}
