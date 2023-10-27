/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.ecommerce.service;

import com.app.ecommerce.entity.Coment;
import com.app.ecommerce.entity.Picture;
import com.app.ecommerce.repository.ProductRepository;
import com.app.ecommerce.entity.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Leona
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository repoProduct;
    @Autowired
    private PictureService servicePictures;

    public void saveNewProduct(String name, float price, Collection<MultipartFile> pictures) throws Exception {
        //Repo pictures para devolver Obj pictures
        if (validateProductName(name)) {
            try {
                Collection<Picture> fotosProducto = servicePictures.savePics(pictures);//Primero se crean las fotos y despues se crea el producto
                Product finalProduct = new Product(name, price, fotosProducto);//Este es el producto final
                repoProduct.save(finalProduct);//Se guarda el producto
            } catch (Exception e) {
                throw new Exception("Error en saveNewProduct Service" + e.getMessage());//Si ocurre un error en este servis salta, si pasa algo en el de Pictures se muestra ese
            }
        }
    }

    public Product findOneProduct(int id) throws Exception {
        try {
            Product product = repoProduct.findById(id).get();//metodo Get ya que es un optional, y puede tirar error
            return product;
        } catch (Exception e) {
            throw new Exception("Error en findOneProductID = " + e.getMessage());
        }
    }

    //Metodo que se usa para buscar productos activos
    public Collection<Product> findAllActiveProduct() throws Exception {
        Collection<Product> products = repoProduct.findByActive();
        return products;
    }

    //Metodo para buscar todos (activos o no), pensado para en algun momento reactivar los que no esten activos
    public Collection<Product> findAllProduct() throws Exception {
        Collection<Product> products = repoProduct.findAll();
        return products;
    }

    //No se borra se actualiza el valor de active = false
    public void deleteProducts(Collection<Integer> ids) throws Exception {
        for (int id : ids) {
            try {
                Product newProduct = repoProduct.findById(id).get();
                newProduct.setActive(false);
                repoProduct.save(newProduct);
            } catch (Exception e) {
                throw new Exception("Error in Deleting Product id = " + id + ": " + e.getMessage());
            }
        }
    }

    //Metodo para actualizar cualquier otra propiedad, se manda un producto ya modificado
    //Este metodo puede ser usado como findOneProduct(ID) *Esta mas arriba* ---> Cambio los atributos Product --> mergeProduct(ProductCambiado)
    public void mergeProduct(Product product) throws Exception {
        try {
            repoProduct.save(product);
        } catch (Exception e) {
            throw new Exception("Error in Merging Product - id = " + product.getId() + ": " + e.getMessage());
        }
    }

    //Metodo para agregar comentarios al producto
    public HttpStatus saveComent(Coment coment , int productId) throws Exception{
        Product product = new Product();
        try {
            product = repoProduct.findById(productId).get();
        }catch (Exception e) {
            throw new Exception("product not found");
        }

        Collection<Coment> comentsProduct = product.getComents();
        if(comentsProduct == null){
            comentsProduct = new ArrayList<Coment>();
            System.out.println("producto no actualizado = "+ product.toString());
        }
        comentsProduct.add(coment);
        product.setComents(comentsProduct);
        System.out.println("producto actualizado = "+product.toString());
        repoProduct.save(product);//guardo los cambios en la bbdd

        return HttpStatus.ACCEPTED;
    }

    /*Servicios privados*/
    private boolean validateProductName(String name) {
        Pattern pattern = Pattern.compile("^\\w*[^0-9@](\\w|\\s)*$");//Se comparan los caracteres del String y si hay alguno que no sea una letra, salta false y no se guarda
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
}
