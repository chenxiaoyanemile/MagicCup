package com.example.sweetgirl.magiccup1.Bean;

/**
 * scene2-1的内容
 */

public class Item {
    public String name;
    public String image;
    public String id;
    public String resource;

    public Item(String name,String image,String id,String resource){
        this.name=name;
        this.image=image;
        this.id=id;
        this.resource=resource;

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
}
