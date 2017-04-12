package com.example.sweetgirl.magiccup1.Bean;

/**
 *  2017/4/10.
 *  {"id":"ab1cdab7-1fca-4ce3-8ef7-777a8d724620",
 *  "name":"星座女2",
 *  "image":"http://v1.qzone.cc/pic/201701/14/23/09/587a3f1bb8dfa264.jpg%21600x600.jpg",
 *  "depiction":"星座女资源",
 *  "resource":"http://ojphnknti.bkt.clouddn.com/scene1/VirgoWoman.assetbundle",
 *  "constellation":"13",
 *  "belong":"1",
 *  "time":"2016-12-16 23:03:07"}
 */

public class SceneOneRequest {
    String id;
    String name;
    String image;
    String depiction;
    String resource;
    String constellation;
    String belong;
    String time;

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
