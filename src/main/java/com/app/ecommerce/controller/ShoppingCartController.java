/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.ecommerce.controller;

import com.app.ecommerce.security.entity.User;

import com.app.ecommerce.service.ShoppingCartService;

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
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    ShoppingCartService scs;

    @PostMapping("/fillCart")
    public String fillCart(ModelMap model,@RequestParam Integer OrderId, @RequestParam int idProduct, @RequestParam Integer IdUser, @RequestParam Integer amount,@RequestParam double totalPrice,  @RequestParam String ShippingAddress, @RequestParam String ShippingWay,@RequestParam boolean OrderSentForAcceptance,@RequestParam String comments) throws Exception {
        
        scs.FillCart(OrderId,idProduct, IdUser, amount,totalPrice,ShippingAddress,ShippingWay,true,comments);
        model.put("exito", "your product was added to the cart");
        return "index";
    }   
    
    @GetMapping("/remove")
    public String removeProduct(ModelMap model,@RequestParam Integer idShoppingCart, @RequestParam Integer idOrder) throws Exception{
        scs.RemoveProduct(idShoppingCart, idOrder);
        model.put("exito", "your product was removed to the cart");
        return "index";
    }
    
    @GetMapping("/finish")
    public String checkOut(ModelMap model,@RequestParam Integer idOrder){
       
        scs.CheckOut(idOrder);
        model.put("totalPrice","El total de su compra es de ");

        return "index";
    }
}
