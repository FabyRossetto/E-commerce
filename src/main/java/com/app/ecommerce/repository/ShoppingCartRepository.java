/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.ecommerce.repository;

import com.app.ecommerce.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Fabi
 */
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String>{
   
    
    @Query("SELECT s FROM ShoppingCart s WHERE s.idShoppingCart = :idShoppingCart ")
    public ShoppingCart lookingScForId(@Param("idShoppingCart") Integer idShoppingCart);
}
