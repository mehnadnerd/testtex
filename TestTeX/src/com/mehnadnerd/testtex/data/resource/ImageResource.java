package com.mehnadnerd.testtex.data.resource;

import javafx.scene.control.TreeItem;

import java.io.File;


/**
 * Created by mehnadnerd on 2016-05-20.
 */
public class ImageResource extends Resource {
    private File imgFile;
    private int width;
    private int height;


    @Override
    public TreeItem toDisplayFormat() {
        return new TreeItem<>(this);
    }

    @Override
    public String toTeXFormat() {
        StringBuilder toRet = new StringBuilder();
        toRet.append("\\includegraphics[width=").append(width).append(",height=").append(height).append("]{");
        toRet.append(imgFile.getAbsolutePath()).append("}\n");
        return toRet.toString();
    }

    @Override
    public String toString() {
        return "Image|" + imgFile.getName();
    }

    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public File getImgFile() {
        return imgFile;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
