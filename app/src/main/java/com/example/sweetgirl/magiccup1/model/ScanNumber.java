package com.example.sweetgirl.magiccup1.model;


/**
 *{
 "msg": "扫描成功",
 "code": 200,
    "data": {
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
 "success": true,
 "error": null
 }
 */

public class ScanNumber {
    private String msg;
    private int code;
    private DataBean data;
    private Boolean success;
    private String error;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}



