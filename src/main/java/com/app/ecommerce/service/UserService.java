/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.ecommerce.service;

import com.app.ecommerce.entity.Address;
import com.app.ecommerce.security.entity.User;
import com.app.ecommerce.security.repository.UserRepository;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Leona
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public HttpStatus createNewAddres(Address address, Integer userId) {
        try {
            System.out.println("Dentro del CreateNew Address");
             User user = new User();
            if(userRepo.findById(userId).isPresent()){
               user = userRepo.findById(userId).get();
            }else{
                throw new Exception("User not Founds");
            }
            List<Address> addresses = user.getAddresses();
            System.out.println(addresses == null);
            if(addresses == null){
                addresses = new LinkedList<Address>();
                System.out.println("Dentro del If adrress == null");
            }

            address.setUser(user);//guardo el Id de su posicion en la mimsma Direccion
            addresses.add(address);//este mismo id lo uso para ordenar la coleccion
            user.setAddresses(addresses);
            userRepo.save(user);//hace un merge si tiene datos en la bbdd

            return HttpStatus.CREATED;
        } catch (Exception e) {
            System.out.println("Error = " + e.getMessage());
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public HttpStatus deleteAddres(Address address, User user) {
        try {
            List<Address> addresses = user.getAddresses();
            addresses.remove(address);
            user.setAddresses(addresses);
            userRepo.save(user);//hace un merge si tiene datos en la bbdd

            return HttpStatus.OK;
        } catch (Exception e) {
            System.out.println("Error = " + e.getMessage());
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public HttpStatus mergeAddres(Address address, User user) {
        try {
            List<Address> addresses = user.getAddresses();

            while(addresses.iterator().hasNext()){
                if(addresses.iterator().equals(address)){
                    addresses.iterator().remove();
                    break;
                }
                addresses.iterator().next();
            }
            userRepo.save(user);//hace un merge si tiene datos en la bbdd

            return HttpStatus.OK;
        } catch (Exception e) {
            System.out.println("Error = " + e.getMessage());
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public List<Address> getAddresess(Integer userId){
        try {
            User user = new User();
            if(userRepo.findById(userId).isPresent()){
                    user = userRepo.findById(userId).get();
            }else{
                throw new Exception("User not Founds");
            }
            List<Address> addresses = user.getAddresses();

            return addresses;
        }catch (Exception e){
            System.out.println("Error = " + e.getMessage());
            return null;
        }
    }
    //----- Servicios privados ---

}
