package com.ysbd.beijing.utils.selectPhoto;

import java.io.Serializable;

public class ImageViewerModel implements Serializable {
    private String path;
    private boolean isSelect;

    public ImageViewerModel() {
    }

    public ImageViewerModel(String path, boolean isSelect) {
        this.path = path;
        this.isSelect = isSelect;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public String toString() {
        return "ImageViewerModel{" +
                "path='" + path + '\'' +
                ", isSelect=" + isSelect +
                '}';
    }
}
