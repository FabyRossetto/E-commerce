/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.ecommerce.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {

    @Id
    //@GeneratedValue(generator = "uuid")
    //@GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue
    private Integer idShoppingCart;
   
    private int idproduct;

    private Integer amount;
    private Double priceProduct;
   @OneToMany
   private List<Product>productsList;

   @ManyToOne
   @JoinColumn(name = "order_id")
   public Order order;



    @Override
    public String toString() {
        return "su carrito tiene " + amount + "productos";
    }
}
