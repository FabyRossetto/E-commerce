/*
  Direccion del Usuario
 */
package com.app.ecommerce.entity;


import com.app.ecommerce.security.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 *
 * @author Leona
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {
    //----- Atributos ------
    @Id
    @GeneratedValue
    private Integer id;
    private String street;//Calle
    private String altitude;//Numeral
    private String city;//Ciudad
    private String contry;//Pais
    private int postalCode;//Codigo postal
    @Embedded
    private LatLong latLong;//Latitud y Longitud

    @Nullable
    @ManyToOne
    @JsonBackReference
    private User user;


    @Override
    public String toString() {
        return "Address = "  + street + " " + altitude + ", " + city + ", " + contry + ", postalCode=" + postalCode;
    }
    
    
    
    
}
