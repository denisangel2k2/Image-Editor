package com.app.imageeditor.Utils;

import javafx.scene.image.*;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;



public class ImageProcessor implements ImageWorker{
    /**
     * Function that converts a javafx Image to a BufferedImage
     * @param image Image
     * @return BufferedImage
     */
    public static BufferedImage toBufferedImage(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        // Create a new ARGB BufferedImage
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Copy the pixel data from the Image object into the BufferedImage
        int[] pixels = new int[width * height];
        image.getPixelReader().getPixels(0, 0, width, height, javafx.scene.image.PixelFormat.getIntArgbInstance(), pixels, 0, width);
        bufferedImage.setRGB(0, 0, width, height, pixels, 0, width);

        return bufferedImage;
    }

    /**
     * Function that replaces the given colored pixels with transparent pixels
     * @param image Image
     * @param transparentColor Color
     * @return Image
     */
    public static Image removeWhiteBackground(Image image, Color transparentColor) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        WritableImage newImage = new WritableImage(width, height);
        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = newImage.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = pixelReader.getColor(x, y);
                if (!color.equals(transparentColor)) {
                    pixelWriter.setColor(x, y, color);
                }
            }
        }

        return newImage;
    }

    /**
     * Function that resizes the Image to new width, height
     * @param image Image
     * @param newWidth int
     * @param newHeight int
     * @return Image
     */
    public static Image resizeImage(Image image, int newWidth, int newHeight) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        WritableImage newImage = new WritableImage(newWidth, newHeight);
        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = newImage.getPixelWriter();

        for (int x = 0; x < newWidth; x++) {
            for (int y = 0; y < newHeight; y++) {
                double scaleX = (double) x / (double) newWidth;
                double scaleY = (double) y / (double) newHeight;
                int oldX = (int) (scaleX * width);
                int oldY = (int) (scaleY * height);
                pixelWriter.setColor(x, y, pixelReader.getColor(oldX, oldY));
            }
        }

        return newImage;
    }
}

