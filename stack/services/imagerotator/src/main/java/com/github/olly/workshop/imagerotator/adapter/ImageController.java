package com.github.olly.workshop.imagerotator.adapter;

import com.github.olly.workshop.imagerotator.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/image")
public class ImageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;

    @PostMapping("rotate")
    public ResponseEntity rotateImage(@RequestParam("image") MultipartFile file, @RequestParam(value = "degrees") String degrees) throws IOException {
        if (file.getContentType() != null &&
                !file.getContentType().startsWith("image/")) {
            LOGGER.warn("Wrong content type uploaded: {}", file.getContentType());
            return new ResponseEntity<>("Wrong content type uploaded: " + file.getContentType(), HttpStatus.BAD_REQUEST);
        }

        // ISSUE: we fail on floating point values
        int intDegrees = Integer.valueOf(degrees);
        LOGGER.info("Receiving {} image to rotate by {} degrees", file.getContentType(), intDegrees);

        byte[] rotatedImage = imageService.rotate(file, intDegrees);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(file.getContentType()));

        LOGGER.info("Successfully rotated image");
        return new ResponseEntity<>(rotatedImage, headers, HttpStatus.OK);
    }
}
