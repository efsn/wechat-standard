package com.codeyn.wechat.sdk.material.result;

import java.util.List;

public class NewsList extends MaterialList {

    private List<NewsItem> item;

    public List<NewsItem> getItem() {
        return item;
    }

    public void setItem(List<NewsItem> item) {
        this.item = item;
    }
    
}
