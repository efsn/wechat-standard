package com.codeyn.wechat.service.material;

import java.util.List;

import com.codeyn.wechat.wc.model.material.NewsArticle;

public class NewsArticleService {

    public static List<NewsArticle> getArticles(Integer newsId) {
        return NewsArticle.me.getArticles(newsId);
    }

    public static NewsArticle getArticleById(Integer id) {
        return NewsArticle.me.findById(id);
    }

    public static void deleteByNewsId(Integer id) {
        NewsArticle.me.deleteByNewsId(id);
    }

    public static void save(NewsArticle article) {
        if (article.getInt("id") == null) {
            article.save();
        } else {
            article.update();
        }
    }

}
