package com.example.sweetgirl.magiccup1.model;

/**
 *
 "dbScene1": {
 "id": "643abd25-ffec-11e6-9354-a4db303d2fd7",
 "name": "我是真的你",
 "image": "http://oexlqeny2.bkt.clouddn.com/cup/cupar1.jpg",
 "depiction": "说这么多都是爱你哟",
 "resource": "http://oexlqeny2.bkt.clouddn.com/scene0.zip",
 "constellation": "12",
 "belong": "1",
 "time": "2017-03-03 16:35:45"
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
