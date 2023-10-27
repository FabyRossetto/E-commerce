/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.app.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.ecommerce.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Leona
 */
public interface ProductRepository extends JpaRepository<Product , Integer>{
    @Query("Select p from Product p WHERE p.active  = true")
    public List<Product> findByActive();
}
