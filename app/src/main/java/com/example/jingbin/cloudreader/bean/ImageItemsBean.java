package com.example.jingbin.cloudreader.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ImageItemsBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<String> imageList;
    private ArrayList<String> imageTitles;

    public ArrayList<String> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

    public ArrayList<String> getImageTitles() {
        return imageTitles;
    }

    public void setImageTitles(ArrayList<String> imageTitles) {
        this.imageTitles = imageTitles;
    }
}
