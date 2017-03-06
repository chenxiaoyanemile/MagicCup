package com.example.sweetgirl.magiccup1.model;

/**
 * {
 "msg": "查询成功",
 "code": 200,
 "data": {
     "user_id": "50fd89bd-bf39-46b5-8bfd-ff2ba4174f7d",
     "text": "我不喜欢你",
     "dbScene1": {
            "id": "643abd25-ffec-11e6-9354-a4db303d2fd7",
            "name": "我是真的你",
            "image": "http://oexlqeny2.bkt.clouddn.com/cup/cupar1.jpg",
            "depiction": "说这么多都是爱你哟",
            "resource": "http://oexlqeny2.bkt.clouddn.com/scene0.zip",
            "constellation": "12",
            "belong": "1",
             "time": "2017-03-03 16:35:45"
                },
     "dbScene2": {
             "id": "643abd25-ffec-11e6-9354-a4db303d2fd7",
            "name": "我是真的你",
             "image": "http://oexlqeny2.bkt.clouddn.com/cup/cupar1.jpg",
            "depiction": "说这么多都是爱你哟",
             "resource": "http://oexlqeny2.bkt.clouddn.com/scene0.zip",
             "constellation": "12",
             "belong": "1",
            "time": "2017-03-03 16:35:45"
                },
 "dbScene31": {
 "id": "643abd25-ffec-11e6-9354-a4db303d2fd7",
 "name": "我是真的你",
 "image": "http://oexlqeny2.bkt.clouddn.com/cup/cupar1.jpg",
 "depiction": "说这么多都是爱你哟",
 "resource": "http://oexlqeny2.bkt.clouddn.com/scene0.zip",
 "constellation": "12",
 "belong": "1",
 "time": "2017-03-03 16:35:45"
 },
 "dbScene32": {
 "id": "643abd25-ffec-11e6-9354-a4db303d2fd7",
 "name": "我是真的你",
 "image": "http://oexlqeny2.bkt.clouddn.com/cup/cupar1.jpg",
 "depiction": "说这么多都是爱你哟",
 "resource": "http://oexlqeny2.bkt.clouddn.com/scene0.zip",
 "constellation": "12",
 "belong": "1",
 "time": "2017-03-03 16:35:45"
 },
 "dbScene33": {
 "id": "643abd25-ffec-11e6-9354-a4db303d2fd7",
 "name": "我是真的你",
 "image": "http://oexlqeny2.bkt.clouddn.com/cup/cupar1.jpg",
 "depiction": "说这么多都是爱你哟",
 "resource": "http://oexlqeny2.bkt.clouddn.com/scene0.zip",
 "constellation": "12",
 "belong": "1",
 "time": "2017-03-03 16:35:45"
 },
 "dbScene4": {
 "id": "643abd25-ffec-11e6-9354-a4db303d2fd7",
 "name": "我是真的你",
 "image": "http://oexlqeny2.bkt.clouddn.com/cup/cupar1.jpg",
 "depiction": "说这么多都是爱你哟",
 "resource": "http://oexlqeny2.bkt.clouddn.com/scene0.zip",
 "constellation": "12",
 "belong": "1",
 "time": "2017-03-03 16:35:45"
 }
 },
 "success": true,
 "error": null
 }
 */

public class ShowScene {
    private String msg;
    private int code;
    private ShowAllScene data;
    private Boolean success;
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

    public ShowAllScene getData() {
        return data;
    }

    public void setData(ShowAllScene data) {
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
