package com.example.sweetgirl.magiccup1.event;

/**
 * UnityResourceEvent on 2017/4/17.
 */

public class UnityResourceEvent {

    private String resource1;
    private String resource2;
    private String resource31;
    private String resource32;
    private String resource33;
    private String resource4;

    public UnityResourceEvent(){

    }

    public UnityResourceEvent(String resource1,String resource2,
                              String resource31,String resource32,
                              String resource33,String resource4){
        this.resource1=resource1;
        this.resource2=resource2;
        this.resource31=resource31;
        this.resource32=resource32;
        this.resource33=resource33;
        this.resource4=resource4;

    }

    public String getResource1() {
        return resource1;
    }

    public void setResource1(String resource1) {
        this.resource1 = resource1;
    }

    public String getResource2() {
        return resource2;
    }

    public void setResource2(String resource2) {
        this.resource2 = resource2;
    }

    public String getResource32() {
        return resource32;
    }

    public void setResource32(String resource32) {
        this.resource32 = resource32;
    }

    public String getResource31() {
        return resource31;
    }

    public void setResource31(String resource31) {
        this.resource31 = resource31;
    }

    public String getResource33() {
        return resource33;
    }

    public void setResource33(String resource33) {
        this.resource33 = resource33;
    }

    public String getResource4() {
        return resource4;
    }

    public void setResource4(String resource4) {
        this.resource4 = resource4;
    }
}
