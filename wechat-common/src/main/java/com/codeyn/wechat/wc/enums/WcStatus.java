package com.codeyn.wechat.wc.enums;

public enum WcStatus {

    YES(1, "是"), NO(0, "否");
    
    private Integer flag;
    private String name;
    
    private WcStatus(Integer flag, String name){
        this.flag = flag;
        this.name = name;
    }
    
    public static WcStatus get(Integer flag){
        for(WcStatus status : values()){
            if(status.getFlag().equals(flag)){
                return status;
            }
        }
        return null;
    }

    public Integer getFlag() {
        return flag;
    }

    public String getName() {
        return name;
    }
    
}
