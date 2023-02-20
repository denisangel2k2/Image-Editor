package com.app.imageeditor.Utils;

import com.app.imageeditor.Constants.ImageType;

public class ImageFactory {
    private static final ImageFactory instance = new ImageFactory();
    public static ImageFactory getInstance(){
        return instance;
    }
    public ImageWorker create(ImageType type) {
        switch (type){
            case SAVER: return new ImageSaver();
            case PROCESSOR:  return new ImageProcessor();
            default: return null;

        }

    }
    private ImageFactory(){}
}
