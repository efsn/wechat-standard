package com.codeyn.wechat.sdk.material.result;

import com.codeyn.wechat.sdk.msg.result.Article;

public class NewsItem {
    
    private String media_id;
    private Article content;
    private Long update_time;
    
    public String getMedia_id() {
        return media_id;
    }
    
    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }
    
    public Article getContent() {
        return content;
    }
    
    public void setContent(Article content) {
        this.content = content;
    }
    
    public Long getUpdate_time() {
        return update_time;
    }
    
    public void setUpdate_time(Long update_time) {
        this.update_time = update_time;
    }
    
}
