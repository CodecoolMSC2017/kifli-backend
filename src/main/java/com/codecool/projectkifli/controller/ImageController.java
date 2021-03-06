package com.codecool.projectkifli.controller;

import com.codecool.projectkifli.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;

    @GetMapping(
            value = "/{id}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] getImageById(@PathVariable("id") String id) {
        return imageService.getById(id);
    }

    @PostMapping(
            value = "",
            consumes = MediaType.IMAGE_JPEG_VALUE
    )
    public void saveImage(@RequestBody byte[] data, @RequestHeader("productId") Integer productId) {
        logger.trace("Post for product {}", productId);
        try {
            imageService.insert(data, productId);
        } catch (IOException e) {
            logger.error("Could not save file", e);
        }
    }

}
