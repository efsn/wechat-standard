package com.codeyn.wechat.sdk.material.result;

import com.codeyn.wechat.sdk.base.model.WxResult;

public class MaterialList extends WxResult{
    
    private Integer total_count;
    private Integer item_count;
    
    public Integer getTotal_count() {
        return total_count;
    }
    
    public void setTotal_count(Integer total_count) {
        this.total_count = total_count;
    }
    
    public Integer getItem_count() {
        return item_count;
    }
    
    public void setItem_count(Integer item_count) {
        this.item_count = item_count;
    }
    
}
