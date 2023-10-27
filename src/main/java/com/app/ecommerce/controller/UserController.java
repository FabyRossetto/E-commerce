/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.ecommerce.controller;

import com.app.ecommerce.entity.Address;
import com.app.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author Leona
 */
@RestController
@RequestMapping("/userService")
public class UserController {

    @Autowired
    private UserService userServis;
    
    @PostMapping("/address/newAddress/{userId}")
    public ResponseEntity<?> createNewAddress(@RequestBody Address address,
            @PathVariable("userId") Integer userId) {

        try {
            HttpStatus status = userServis.createNewAddres(address, userId);
            if (status.is2xxSuccessful()) {
                return new ResponseEntity<>("New address created", status);
            }else{
                return new ResponseEntity<>("Not created, STATUS = "+ status.getReasonPhrase() , status);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("An error Ocurr = " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/address/getAddress/{userId}")
    public  ResponseEntity<?> getUserAddresses(@PathVariable("userId") Integer userId){
        try {
            List<Address> addresses = userServis.getAddresess(userId);
            if (!(addresses.isEmpty())) {
                return new ResponseEntity<>(addresses, HttpStatus.FOUND);
            }else{
                return new ResponseEntity<>("Not found, STATUS = " , HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An error Ocurr = " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
