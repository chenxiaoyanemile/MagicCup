package com.example.sweetgirl.magiccup1.Bean;

import com.example.sweetgirl.magiccup1.model.UserBean;

/**
 *{"scanNumber":"1","
 message":"success",
 "user":{
 "id":"2140ce69-a5e6-402c-b4d7-11a29c0becc2",
 "serial":"1486519379841niI34iXeTr4xhNlAH4K2hrKMt",
 "createTime":"2017-03-29 21:07:51","
 updateTime":"2017-03-29 21:07:51","
 user_id":"3b8f35b9-4d84-4a2c-a30d-de8678b73b28","
 sex":null,"
 constellation":null}}
 */

public class ScanCodeResult {

    private int scanNumber;
    private String message;
    private UserBean user;

    public int getScanNumber() {
        return scanNumber;
    }

    public void setScanNumber(int scanNumber) {
        this.scanNumber = scanNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }
}
