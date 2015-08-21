package com.codeyn.wechat.sdk.material.result;

public class MediaItem {
    
    private String media_id;
    private String name;
    private Long update_time;
    private String url;
    
    public String getMedia_id() {
        return media_id;
    }
    
    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Long getUpdate_time() {
        return update_time;
    }
    
    public void setUpdate_time(Long update_time) {
        this.update_time = update_time;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

}
