package com.example.sweetgirl.magiccup1.model;

/**
 * "data": {
    "scanNumber": "1",
    "user": {
    "id": "d0e38218-f5a8-11e6-8d11-a4db303d2fd7",
    "serial": "1487401609152D0uueoIg9b47rqSSJ53bdNu60",
     "createTime": "2017-02-18 15:07:56",
     "updateTime": null,
     "user_id": "ad5a54b1-116d-4f80-93f4-b8dd8fcb7e9a",
     "sex": null,
    "constellation": null
 }
 },
 */

public class DataBean {

    private String scanNumber;
    private  String message;
    private UserBean user;

    public String getScanNumber() {
        return scanNumber;
    }

    public void setScanNumber(String scanNumber) {
        this.scanNumber = scanNumber;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }
}
