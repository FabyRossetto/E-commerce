/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.ecommerce.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Leona
 */
@Entity
public class Picture implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;//nombre
    private String mime;//tipo (jpg, blabla)

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name="photocontent" , columnDefinition="BLOB")
    private byte[] content;//Bytes de la foto
    
    /*------- Constructor -----*/
    
    public Picture() {
    }

    public Picture(String name, String mime, byte[] content) {
        this.name = name;
        this.mime = mime;
        this.content = content;
    }
    
    /*----- Getters & Setters -------*/
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the nombre to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the mime
     */
    public String getMime() {
        return mime;
    }

    /**
     * @param mime the mime to set
     */
    public void setMime(String mime) {
        this.mime = mime;
    }

    /**
     * @return the contenido
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * @param content the contenido to set
     */
    public void setContent(byte[] content) {
        this.content = content;
    }

}
