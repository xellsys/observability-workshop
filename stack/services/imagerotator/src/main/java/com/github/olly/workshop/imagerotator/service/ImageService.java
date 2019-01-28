package com.github.olly.workshop.imagerotator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    @Autowired
    MetricsService metricsService;

    public byte[] rotate(MultipartFile file, int degrees) {
        try {
            InputStream in = new ByteArrayInputStream(file.getBytes());
            String formatName = file.getContentType().split("/")[1];
            final BufferedImage rotatedImage = rotate(ImageIO.read(in), degrees);
            final byte[] imageBytes = bufferedImageToByteArray(rotatedImage, formatName);

            metricsService.imageRotated(file.getContentType(), String.valueOf(degrees));

            return imageBytes;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private BufferedImage rotate(BufferedImage image, int degrees) {
        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(degrees), image.getWidth() / 2, image.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        return op.filter(image, null);
    }

    private byte[] bufferedImageToByteArray(BufferedImage image, String formatname) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, formatname, baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        return imageInByte;
    }
}