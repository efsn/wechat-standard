package com.codeyn.wechat.sdk.menu.result;

import java.util.List;

import com.codeyn.wechat.sdk.base.model.WcResult;

public class Menu extends WcResult{

    /**
     * 一级菜单数组，个数应为1~3个
     */
    private List<Menu> button;
    
    /**
     * 二级菜单数组，个数应为1~5个
     */
    private List<Menu> sub_button;
    
    /**
     * 菜单的响应动作类型
     */
    private String type;
    
    /**
     * 菜单标题，不超过16个字节，子菜单不超过40个字节
     */
    private String name;
    
    /**
     * 菜单KEY值，用于消息接口推送，不超过128字节
     */
    private String key;
    
    /**
     * 网页链接，用户点击菜单可打开链接，不超过256字节
     */
    private String url;
    
    /**
     * 永久素材media_id
     */
    private String media_id;

    public List<Menu> getButton() {
        return button;
    }

    public void setButton(List<Menu> button) {
        this.button = button;
    }

    public List<Menu> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<Menu> sub_button) {
        this.sub_button = sub_button;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }
    
    public String getValue(){
        if(type == null) return null;
        switch (type) {
            case "view" :
                return url;

            case "click" :
                return key;

            case "media_id" :
            case "view_limited" :
                return media_id;

            default :
                return null;
        }
    }
    
}
