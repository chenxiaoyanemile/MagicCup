package com.example.sweetgirl.magiccup1.model;

/**
 *   "user": {
        "id": "d0e38218-f5a8-11e6-8d11-a4db303d2fd7",
         "serial": "1487401609152D0uueoIg9b47rqSSJ53bdNu60",
         "createTime": "2017-02-18 15:07:56",
         "updateTime": null,
         "user_id": "ad5a54b1-116d-4f80-93f4-b8dd8fcb7e9a",
         "sex": null,
         "constellation": null
 }
 */

public class UserBean {
    private String id;
    private String serial;
    private String createTime;
    private String updateTime;
    private String user_id;
    private String sex;
    private String constellation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }
}
