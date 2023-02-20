package com.app.imageeditor.Constants;

public enum ImageExtension {
    PNG("png"),
    TGA("tga"),
    JPG("jpg");

    private final String text;
    ImageExtension(String text) {
        this.text=text;
    }
    public String toString() {
        return this.text;
    }
}
