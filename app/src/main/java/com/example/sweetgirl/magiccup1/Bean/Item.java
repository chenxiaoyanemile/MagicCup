package com.example.sweetgirl.magiccup1.Bean;

/**
 * scene2-1的内容
 */

public class Item {
    public String name;
    public String image;

    public Item(String name,String image){
        this.name=name;
        this.image=image;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
