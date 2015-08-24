package com.codeyn.wechat.sdk.material.result;

import com.codeyn.wechat.sdk.base.model.WcResult;

/**
 * 视频消息素材
 * @author Arthur
 *
 */
public class Video extends WcResult{

    private String title;
    
    private String description;
    
    private String down_url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDown_url() {
        return down_url;
    }

    public void setDown_url(String down_url) {
        this.down_url = down_url;
    }
    
}
