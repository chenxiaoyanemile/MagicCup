package com.example.sweetgirl.magiccup1.Bean;

/**
 * 场景1的材料
 * String id;
 String name;
 String image;
 String depiction;
 String resource;
 String constellation;
 String belong;
 String time;
 */

public class TextImage {

    public String id;
    public String name;
    public String dep;
    public String image;
    public String resource;


    public TextImage(String id,String image, String text,String text1,String resource) {
        this.id=id;
        this.image = image;
        this.name = text;
        this.dep=text1;
        this.resource=resource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
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
