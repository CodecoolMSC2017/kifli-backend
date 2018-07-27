package com.codecool.projectkifli.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ImageController {

    @GetMapping(
            value = "/{id}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] getImageById(@PathVariable("id") String id) {
        String url = System.getProperty("user.home") + "/kifli-images";
        if (new File(url).mkdir()) {
            return null;
        }
        try {
            String path = url + "/" + id;
            File file = getExistingFile(path);
            BufferedImage image = ImageIO.read(file);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpeg", baos);

            return baos.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }

    private File getExistingFile(String path) {
        File file = new File(path + ".jpg");
        if (!file.exists()) {
            file = new File(path + ".jpeg");
        }
        if (!file.exists()) {
            file = new File(path + ".JPG");
        }
        if (!file.exists()) {
            file = new File(path + ".JPEG");
        }
        return file;
    }

}
