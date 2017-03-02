package com.example.sweetgirl.magiccup1.model;

/**
 *   "dbScene1": {
 "id": "105492c5-3654-446b-a649-852802f2bb14",
 "name": "默认部分",
 "image": "http://oexlqeny2.bkt.clouddn.com/2.jpg",
 "depiction": "默认",
 "resource": "http://oexlqeny2.bkt.clouddn.com/scene1/DYM.assetbundle",
 "constellation": "13",
 "belong": "1",
 "time": "2016-12-16 15:25:41"
 },
 */

public class Scene1 {
    private String id;
    private String name;
    private String image;
    private String depiction;
    private String resource;
    private String constellation;
    private String belong;
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDepiction() {
        return depiction;
    }

    public void setDepiction(String depiction) {
        this.depiction = depiction;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
