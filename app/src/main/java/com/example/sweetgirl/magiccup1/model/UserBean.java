package com.example.sweetgirl.magiccup1.model;

/**
 * "user": {
 "id": "082c9098-0326-4f18-8f5d-5e0494d9c6ab",
 "serial": "1478571500302eSxpLEBIR9k3HogJQWLI6znTS",
 "createTime": "2016-11-27 16:37:27",
 "updateTime": "2016-11-27 16:37:27",
 "user_id": "f56c827f-027f-49e0-8b35-be3860915537",
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
