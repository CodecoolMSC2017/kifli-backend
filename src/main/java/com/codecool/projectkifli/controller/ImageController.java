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
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public byte[] getImageById(@PathVariable("id") String id) throws IOException {
        String url = System.getProperty("user.home") + "/kifli-images";
        if (new File(url).mkdir()) {
            return null;
        }
        String path = url + "/" + id + ".png";
        BufferedImage image = ImageIO.read(new File(path));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);

        return baos.toByteArray();
    }

}
