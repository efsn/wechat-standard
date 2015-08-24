package com.codeyn.wechat.wc.model.material;

import java.util.List;

import com.codeyn.jfinal.annos.JFinalAnnos.ModelMapping;
import com.codeyn.wechat.wc.enums.WcMaterialType;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@ModelMapping("wc_news")
public class News extends Model<News> {

    private static final long serialVersionUID = 1L;

    public static final News me = new News();

    public News() {
    }

    public News(String tenantId, WcMaterialType type) {
        set("tenant_id", tenantId);
        set("type", type.getFlag());
    }

    public Page<News> pagations(int pageNumber, int pageSize, String tenantId, WcMaterialType type) {
        String select = "select * ";
        String sql = "from wx_news where tenant_id = ? and type = ? order by create_time desc";
        return News.me.paginate(pageNumber, pageSize, select, sql, tenantId, type.getFlag());
    }

    public List<NewsArticle> getArticles() {
        String sql = "select * from wc_news_article from where news_id order by weight asc";
        return NewsArticle.me.find(sql, getInt("id"));
    }

}
