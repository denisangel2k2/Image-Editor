package com.app.imageeditor.Utils;

import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;



public class ImageSaver implements ImageWorker {



    public void saveToFile(BufferedImage image, String name,String extension, String location) throws IOException
    {
        File output=new File(location+"\\"+name+"."+extension);
        ImageIO.write(image,extension,output);
    }
}
