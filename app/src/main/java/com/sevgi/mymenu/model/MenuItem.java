package com.sevgi.mymenu.model;

/**
 * Created by sevgiozturk on 06/05/16.
 */

public class MenuItem {
    private String name;
    private int imageResource;

    public MenuItem(String name, int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageRes() {
        return imageResource;
    }

    public void setImageRes(int imageResource) {
        this.imageResource = imageResource;
    }
}
