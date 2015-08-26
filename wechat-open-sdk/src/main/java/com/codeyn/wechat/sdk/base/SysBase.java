package com.codeyn.wechat.sdk.base;

public class SysBase{

    private String host = "";

    private String name;

    public SysBase(String host, String name){
        this.host = host;
        this.name = name;
    }

    public String getHost(){
        return host;
    }

    public void setHost(String host){
        this.host = host;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

}
