/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.ecommerce.controller;

import com.app.ecommerce.entity.Coment;
import com.app.ecommerce.entity.Product;
import com.app.ecommerce.service.ProductService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Leona
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService servisProducto;

    @GetMapping("/panel")
    public String initPageAdmin() {
        return "panelAdmin.html";
    }

    @PostMapping("/saveProduct")
    public ResponseEntity<?> saveNewProduct(@RequestParam("productName") String productName,
            @RequestParam("productPrice") float price,
            @RequestParam("files[]") Collection<MultipartFile> files) {
        try {
            servisProducto.saveNewProduct(productName, price, files);
            return new ResponseEntity<>("a new product was successfully created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(("Ocurrion un error interno en el servidor :( : " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/seachProduct/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") int idProduct) {
        ModelMap model = new ModelMap();
        try {
            Product product = servisProducto.findOneProduct(idProduct);
            model.addAttribute("product", product);
            return new ResponseEntity<>(model,HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Product not Found",HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/seachAllProducts")
    public ResponseEntity<?> getAllActiveProduct() {
        ModelMap model = new ModelMap();
        try {
            Collection<Product> products = servisProducto.findAllActiveProduct();
            model.addAllAttributes(products);
            return new ResponseEntity<>(model,HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Products not Found",HttpStatus.NOT_FOUND);
        }
    }
    
    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteProduct/{ids}")
    public ResponseEntity<?> delete(@PathVariable("ids") Collection<Integer> ids) {
        try {
            servisProducto.deleteProducts(ids);
            return new ResponseEntity<>("Everithings ok",HttpStatus.OK);
        } catch (Exception e) {
             return new ResponseEntity<>("An error ocurrs xd: message = " + e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/addComment/{productId}")
    public ResponseEntity<?> addNewComment(@PathVariable("productId") int productId,
            @RequestBody Coment coment){
        try {
            HttpStatus status = servisProducto.saveComent(coment, productId);
            return new ResponseEntity("Comentario añadido con exito" , status);
        } catch (Exception e) {
            return new ResponseEntity("An error Ocurr = " + e.toString() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
