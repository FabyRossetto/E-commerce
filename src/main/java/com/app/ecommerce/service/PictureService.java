/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.ecommerce.service;

import com.app.ecommerce.entity.Picture;
import com.app.ecommerce.repository.PictureRepository;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Leona
 */
@Service
public class PictureService {

    @Autowired
    private PictureRepository repoPicture;

    public Collection<Picture> savePics(Collection<MultipartFile> files) throws Exception{
        Collection<Picture> finalPics = new ArrayList<Picture>();

        for (MultipartFile file : files) {
            Picture finalFoto = new Picture();
            try {
                finalFoto.setName(file.getName());
                finalFoto.setMime(file.getContentType());
                finalFoto.setContent(file.getBytes());

                repoPicture.save(finalFoto);
                finalPics.add(finalFoto);          
            } catch (Exception e) {
               throw new Exception("Error en savePics Service");
            }
        }
        return finalPics;
    }

}
