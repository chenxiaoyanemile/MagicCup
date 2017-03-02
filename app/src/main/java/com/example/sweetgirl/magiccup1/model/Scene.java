package com.example.sweetgirl.magiccup1.model;

/**
 *
 *{"data":
 * [{"id":"44ab9e34-077a-4d47-bce6-606e4e6dcb39",
 * "name":"踢下水 拥抱",
 * "image":"http://oexlqeny2.bkt.clouddn.com/3.jpg",
 * "depiction":"踢踢更健康",
 * "resource":"http://oexlqeny2.bkt.clouddn.com/scene2/secondanimation.assetbundle",
 * "constellation":"13",
 * "belong":"2",
 * "time":"2016-12-16 15:31:29"}
 * ]}
 */


public class Scene {
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
