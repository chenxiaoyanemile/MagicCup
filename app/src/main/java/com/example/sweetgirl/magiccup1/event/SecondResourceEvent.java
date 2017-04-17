package com.example.sweetgirl.magiccup1.event;

/**
 * SecondResourceEvent 2017/4/17.
 */

public class SecondResourceEvent {
    private String mName;
    private String mId;
    private String mResource;
    public SecondResourceEvent(){

    }
    public SecondResourceEvent(String mName,String mId,String mResource){
        this.mName=mName;
        this.mId=mId;
        this.mResource=mResource;

    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmResource() {
        return mResource;
    }

    public void setmResource(String mResource) {
        this.mResource = mResource;
    }
}
