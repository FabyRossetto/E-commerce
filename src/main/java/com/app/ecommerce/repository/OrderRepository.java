/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.ecommerce.repository;

import com.app.ecommerce.entity.Order;
import com.app.ecommerce.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Fabi
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, String>{
    
    @Query("SELECT o FROM Order o WHERE o.user.id = :IdUser and o.dateOfPurchase is null")
    public Order lookingForUnfinishedOrders(@Param("IdUser") Integer user);
    @Query("SELECT o FROM Order o WHERE o.id = :id ")
    public Order lookingOrderForId(@Param("id") Integer id);
}
