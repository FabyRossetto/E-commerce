/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.ecommerce.service;

import com.app.ecommerce.Enum.OrderStatus;
import com.app.ecommerce.entity.Order;
import com.app.ecommerce.entity.ShoppingCart;
import com.app.ecommerce.repository.OrderRepository;
import com.app.ecommerce.security.entity.User;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Fabi
 */
@Service
public class OrderService {

    @Autowired
    OrderRepository OrderRepo;

//    this method looks for the purchase order by its id, and deletes it
    public void cancelOrder(Integer idOrder) {
        try {
            Order gettingOrder = OrderRepo.lookingOrderForId(idOrder);
            if (gettingOrder!=null) {
                
                OrderRepo.delete(gettingOrder);
            }

        } catch (Exception e) {
            e.getMessage();
        }
    }
//This method creates the purchase order and saves it
    @Transactional
    public Order startOrder(Integer idOrder, Integer idUser, List<ShoppingCart> sc, double totalPrice,  String ShippingAddress, String ShippingWay, boolean OrderSentForAcceptance, String comments) {
        try {
            Order startingOrder = new Order();
               startingOrder.setIdOrder(idOrder);
               
               startingOrder.setSc(sc);
                startingOrder.setTotalPrice(totalPrice);
                startingOrder.setStatus(OrderStatus.PENDING);
                startingOrder.setDateOfPurchase(new Date());
                startingOrder.setShippingAddress(ShippingAddress);
                startingOrder.setShippingWay(ShippingWay);
                startingOrder.setOrderSentForAcceptance(OrderSentForAcceptance);
                startingOrder.setComments(comments);

             return OrderRepo.save(startingOrder);
            
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
        
    }


}
