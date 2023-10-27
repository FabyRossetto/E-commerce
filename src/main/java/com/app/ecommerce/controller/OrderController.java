/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.ecommerce.controller;

import com.app.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Fabi
 */
@RestController
@RequestMapping("/order")
public class OrderController {
     @Autowired
    OrderService orderService;

    @GetMapping("/cancel")
    public String cancelOrder(ModelMap model, @RequestParam Integer idOrder) {
        orderService.cancelOrder(idOrder);
        model.put("exito", "the order was canceled successfully");

        return "index";
    }


}
