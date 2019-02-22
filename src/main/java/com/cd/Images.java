package com.cd;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by William on 5/20/2017.
 */
public class Images {

    private static ConcurrentHashMap<String, BufferedImage> imageCache = new ConcurrentHashMap<>();

    public static BufferedImage load(String name) {
        BufferedImage image = imageCache.get(name);
        if (image != null) {
            return image;
        }

        File file = new File("res/images", name);

        if (!file.exists()) {
            throw new IllegalArgumentException("File " + file.getPath() + "does not exist!");
        }

        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        imageCache.putIfAbsent(name, image);
        return image;
    }

}
