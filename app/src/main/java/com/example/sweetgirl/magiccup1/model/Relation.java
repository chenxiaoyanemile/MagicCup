package com.example.sweetgirl.magiccup1.model;



public class Relation {

    private String msg;
    private int code;

    private ShowScene showScene;

    private String success;
    private String error;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ShowScene getShowScene() {
        return showScene;
    }

    public void setShowScene(ShowScene showScene) {
        this.showScene = showScene;
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
