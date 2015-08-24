package com.codeyn.wechat.service.material;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.codeyn.wechat.wc.enums.WcMaterialType;
import com.codeyn.wechat.wc.enums.WcStatus;
import com.codeyn.wechat.wc.model.material.News;
import com.codeyn.wechat.wc.model.material.NewsArticle;
import com.jfinal.plugin.activerecord.Page;
import com.codeyn.base.common.Assert;
import com.codeyn.wechat.sdk.base.WcClientFactory;
import com.codeyn.wechat.sdk.material.MaterialClient;
import com.codeyn.wechat.sdk.material.enums.MediaType;
import com.codeyn.wechat.sdk.material.result.Media;
import com.codeyn.wechat.sdk.material.result.NewsItem;
import com.codeyn.wechat.sdk.material.result.NewsList;
import com.codeyn.wechat.sdk.msg.result.Article;
import com.codeyn.wechat.wc.utils.WcCache;

public class NewsService {

    private static MaterialClient getClient(String tenantId) {
        return WcClientFactory.getClient(MaterialClient.class, WcCache.getWxBase(tenantId));
    }

    public static Page<News> pagations(int pageNumber, int pageSize, String tenantId, WcMaterialType type) {
        return News.me.pagations(pageNumber, pageSize, tenantId, type);
    }

    /**
     * 保存图文
     */
    public static boolean save(News news) {
        Date now = new Date();
        news.set("update_time", now);
        if (news.getInt("id") == null) {
            news.set("create_time", now);
            return news.save();
        } else {
            return news.update();
        }
    }

    public static News getNewsById(Integer id) {
        return News.me.findById(id);
    }

    /**
     * 获取图文详情
     */
    public static String getArticles(News news) {
        List<NewsArticle> list = news.getArticles();
        JSONArray array = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            NewsArticle temp = list.get(i);
            JSONObject obj = new JSONObject();
            obj.put("title", temp.get("title"));
            obj.put("content", temp.get("content"));
            obj.put("digest", temp.get("digest"));
            obj.put("url", temp.get("url"));
            obj.put("cover_pic_url", temp.get("cover_pic_url"));
            array.add(obj);
        }
        return array.toString();
    }

    public static boolean deleteById(Integer id) {
        return News.me.deleteById(id);
    }

    /**
     * 从微信服务器同步图文
     */
    public static void sync(String tenantId){
        String accessToken = WcCache.getAccessToken(tenantId);
        
        // 分页获取(0, 20)
        NewsList newsList = getClient(tenantId).getNewsList(accessToken, 0, 20);
        // 回调校验accessToken是否有效
        WcCache.refreshAccessTokenIfInvalid(newsList, tenantId);
        
        List<NewsItem> items = new ArrayList<>();
        if(newsList != null && newsList.isSuccess()){
            items.addAll(newsList.getItem());
            int count = newsList.getTotal_count();
            int pages = count % 20 > 0 ? count / 20 + 1 : count / 20 ;
            for(int i = 1; i < pages; i++){
                NewsList list = getClient(tenantId).getNewsList(accessToken, i, 20);
                if(list != null && list.isSuccess()){
                    items.addAll(newsList.getItem());
                }
            }
        }
        saveNews(tenantId, items);
    }
    
    /**
     * 性能欠妥 TODO 封装batch/save
     */
    private static void saveNews(String tenantId, List<NewsItem> list){
        for(NewsItem item : list){
            News news = new News(tenantId, WcMaterialType.MPNEWS);
            Integer isMultiple = item.getContent().getNews_item().size() > 1 ? WcStatus.YES.getFlag() : WcStatus.NO.getFlag();
            news.set("is_multiple", isMultiple);
            news.set("media_id", item.getMedia_id());
            Date time = new Date(item.getUpdate_time());
            news.set("create_time", time);
            news.set("create_time", time);
            
            Assert.isTrue(news.save(), "news save fail");;
            Integer newsId = news.getInt("id");
            
            List<Article> alist  = item.getContent().getNews_item();
            for(int i = 0; i < alist.size(); i++){
                NewsArticle article = new NewsArticle(newsId);
                article.set("title", alist.get(i).getTitle());
                article.set("digest", alist.get(i).getDigest());
                article.set("author", alist.get(i).getAuthor());
                article.set("thumb_media_id", alist.get(i).getThumb_media_id());
                article.set("show_cover_pic", alist.get(i).getShow_cover_pic());
                
                // 根据media_id获取文件上传至upyun
                article.set("cover_pic_url", "http://codeyn.com");
                
                article.set("content", alist.get(i).getContent());
                article.set("content_source_url", alist.get(i).getContent_source_url());
                article.set("weight", i + 1);
                article.save();
            }
        }
    }
    
    /**
     * 发布图文到微信服务器
     */
    public static Media uploadNews(String tenantId, Integer newsId) {
        List<NewsArticle> list = NewsArticleService.getArticles(newsId);
        
        String accessToken = WcCache.getAccessToken(tenantId);
        Media rs = getClient(tenantId).uploadNews(accessToken, getNewsJson(list, accessToken, tenantId));

        // 回调校验accessToken是否有效
        WcCache.refreshAccessTokenIfInvalid(rs, tenantId);
        return rs;
    }

    private static String getNewsJson(List<NewsArticle> list, String accessToken, String tenantId) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            NewsArticle na = list.get(i);
            String imageUrl = na.getStr("cover_pic_url");
            if (StringUtils.isNotBlank(imageUrl)) {
                String mediaId = WxMediaService.upload(tenantId, MediaType.THUMB, imageUrl, true);

                JSONObject item = new JSONObject();
                item.put("thumb_media_id", mediaId);
                item.put("author", "商圈圈");
                item.put("title", na.getStr("title"));
                item.put("content_source_url", na.getStr("content_source_url"));
                item.put("content", na.getStr("content"));
                item.put("digest", na.getStr("digest"));
                item.put("show_cover_pic", na.getInt("show_cover_pic"));
                array.add(item);
            }
        }
        json.put("articles", array);
        return json.toJSONString();
    }

}
