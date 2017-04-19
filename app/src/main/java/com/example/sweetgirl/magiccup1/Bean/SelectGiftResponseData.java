package com.example.sweetgirl.magiccup1.Bean;

/**
 * {
 "msg": "修改成功",
 "code": 200,
 "data": {
 "user_id": "50fd89bd-bf39-46b5-8bfd-ff2ba4174f7d",
 "text": "我不喜欢你",
 "scene1_id": "643abd25-ffec-11e6-9354-a4db303d2fd7",
 "scene2_id": "643abd25-ffec-11e6-9354-a4db303d2fd7",
 "scene31_id": "643abd25-ffec-11e6-9354-a4db303d2fd7",
 "scene32_id": "643abd25-ffec-11e6-9354-a4db303d2fd7",
 "scene33_id": "643abd25-ffec-11e6-9354-a4db303d2fd7",
 "scene4_id": "643abd25-ffec-11e6-9354-a4db303d2fd7"
 },
 "success": true,
 "error": null
 }
 */

public class SelectGiftResponseData {

    private String msg;
    private String code;
    private TatalSelectData data;
    private String success;
    private String error;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public TatalSelectData getData() {
        return data;
    }

    public void setData(TatalSelectData data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
