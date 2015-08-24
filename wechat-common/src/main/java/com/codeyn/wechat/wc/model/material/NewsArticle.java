package com.codeyn.wechat.wc.model.material;

import java.util.List;

import com.codeyn.jfinal.annos.JFinalAnnos.ModelMapping;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

@ModelMapping("wc_news_article")
public class NewsArticle extends Model<NewsArticle> {

    private static final long serialVersionUID = 1L;

    public static final NewsArticle me = new NewsArticle();

    public NewsArticle() {
    }

    public NewsArticle(Integer newsId) {
        set("news_id", newsId);
    }

    public List<NewsArticle> getArticles(Integer newsId) {
        String sql = "select * from wc_news_article where news_id=? order by weight asc";
        return find(sql, newsId);
    }

    public int deleteByNewsId(Integer newsId) {
        String sql = "delete from wc_news_article where news_id = ?";
        return Db.update(sql, newsId);
    }

}
