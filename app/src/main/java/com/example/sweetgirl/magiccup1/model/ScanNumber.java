package com.example.sweetgirl.magiccup1.model;


/**
 *"scanNumber": "1",
 "message": "success",
 "user": {
 "id": "082c9098-0326-4f18-8f5d-5e0494d9c6ab",
 "serial": "1478571500302eSxpLEBIR9k3HogJQWLI6znTS",
 "createTime": "2016-11-27 16:37:27",
 "updateTime": "2016-11-27 16:37:27",
 "user_id": "f56c827f-027f-49e0-8b35-be3860915537",
 "sex": null,
 "constellation": null
 }
 */

public class ScanNumber {
    private String scanNumber;
    private String message;
    private UserBean user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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



