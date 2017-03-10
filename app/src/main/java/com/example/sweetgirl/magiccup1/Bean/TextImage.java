package com.example.sweetgirl.magiccup1.Bean;

/**
 * 场景1的材料
 */

public class TextImage {

    public String name;
    public String dep;
    public String image;

    public TextImage(String image, String text,String text1) {
        this.image = image;
        this.name = text;
        this.dep=text1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
