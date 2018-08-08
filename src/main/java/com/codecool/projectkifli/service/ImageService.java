package com.codecool.projectkifli.service;

import com.codecool.projectkifli.model.ProductPicture;
import com.codecool.projectkifli.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);
    private static final String imagesDir = System.getProperty("user.home") + "/kifli-images";

    @Autowired
    private ImageRepository imageRepository;

    public byte[] getById(String id) {
        if (new File(imagesDir).mkdir()) {
            logger.error("kifli-images did not exist on this computer");
            return null;
        }
        try {
            String path = imagesDir + "/" + id;
            File file = getExistingFile(path);
            BufferedImage image = ImageIO.read(file);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpeg", baos);

            return baos.toByteArray();
        } catch (IOException e) {
            logger.error("Error sending image {}", id, e);
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

    public void insert(byte[] data, Integer productId) {
        ProductPicture productPicture = new ProductPicture();
        productPicture.setProductId(productId);

        ProductPicture save = imageRepository.save(productPicture);
        logger.debug("Inserted picture {} for product {}", save.getId(), save.getProductId());

        saveFile(save.getId(), productId, data);
    }

    private void saveFile(Integer id, Integer productId, byte[] data) {
        String path = imagesDir + "/" + id + ".jpg";
        File file = new File(path);
        if (!file.exists()) {
            try {
                logger.trace("Creating file {}", path);
                file.createNewFile();
            } catch (IOException e) {
                logger.error("Error creating file {}", path, e);
            }
        } else {
            logger.warn("File {} already exists", path);
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            logger.debug("Saving file {}", path);
            fileOutputStream.write(data);
            logger.info("Saved image {} for product {}", id, productId);
        } catch (FileNotFoundException e) {
            logger.error("File {} does not exist", path, e);
        } catch (IOException e) {
            logger.error("Error creating file {}", path, e);
        }
    }
}
