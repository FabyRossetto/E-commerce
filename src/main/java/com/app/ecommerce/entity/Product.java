/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.ecommerce.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
/**
 *
 * @author Leona
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="product")
public class Product {
    /* Atributos */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;//Generado tipo 1-2-3-...
    private String name;
    private float price;//Precio
    private boolean active;//Boleano para activar o desactivar los productos, para mantener la integridad de la bb y no borrrar datos. Si no se quiere mas un producto se desactiva
    
    @NotNull
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_pictures", joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "picture_id"))
    private Collection<Picture> picture;//entidad Picture

    @Nullable
    @OneToMany(fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST , CascadeType.REMOVE ,CascadeType.MERGE})
    private Collection<Coment> coments;//Entidad Coment
    
    /*Constructores */

    public Product(int id, String name, float price, boolean active, Collection<Picture> picture) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.active = active;
        this.picture = picture;
    }

    public Product(String name, float price, Collection<Picture> picture) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.picture = picture;
        this.active = true;
        this.coments = new ArrayList<Coment>();//Podemos usar HashMap o TreeMap (El HashMap creo que no va a funcionar porque permitiria 1 solo comentario x Usuario)
    }

    /*Constructor para crear un producto con comentarios y fotos*/
    public Product(String name, float price, Collection<Picture> picture, Collection<Coment> coments) {
        this.name = name;
        this.price = price;
        this.picture = picture;
        this.coments = coments;
        this.active = true;
    }
    
    /*Getters and Setters*/

    public Collection<Coment> getComents() {
        return coments;
    }

    public void setComents(Collection<Coment> coments) {
        this.coments = coments;
    }
    
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Collection<Picture> getPicture() {
        return picture;
    }

    public void setPicture(Collection<Picture> picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", active=" + active +
                ", picture=" + picture +
                ", coments=" + coments.toString() +
                '}';
    }
}
