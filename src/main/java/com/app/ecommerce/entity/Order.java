/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.ecommerce.entity;


import com.app.ecommerce.Enum.OrderStatus;
import com.app.ecommerce.security.entity.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Fabi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_T")
public class Order {
    @Id
    //@GeneratedValue(generator = "uuid")
    //@GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue
    private Integer idOrder;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    @OneToMany(mappedBy = "order")
    private List<ShoppingCart>sc;

    private Double totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatus Status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfPurchase;
    private String ShippingAddress;
    private String ShippingWay;
    private boolean OrderSentForAcceptance;
    private String comments;

    
    @Override
    public String toString() {
        return "su numero de orden es" +getIdOrder();
    }
 
}
