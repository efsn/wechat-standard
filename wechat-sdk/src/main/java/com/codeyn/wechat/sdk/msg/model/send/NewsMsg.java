package com.codeyn.wechat.sdk.msg.model.send;

import java.util.ArrayList;
import java.util.List;

import com.codeyn.wechat.sdk.msg.enums.MsgType;
import com.codeyn.wechat.sdk.msg.model.receive.ReceivedMsg;

/**
	回复图文消息
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>12345678</CreateTime>
		<MsgType><![CDATA[news]]></MsgType>
			<ArticleCount>2</ArticleCount>
			<Articles>
				<item>
					<Title><![CDATA[title1]]></Title> 
					<Description><![CDATA[description1]]></Description>
					<PicUrl><![CDATA[picurl]]></PicUrl>
					<Url><![CDATA[url]]></Url>
				</item>
				
				<item>
					<Title><![CDATA[title]]></Title>
					<Description><![CDATA[description]]></Description>
					<PicUrl><![CDATA[picurl]]></PicUrl>
					<Url><![CDATA[url]]></Url>
				</item>
			</Articles>
	</xml> 
 */
public class NewsMsg extends SentMsg {
    
	private List<NewsItem> articles = new ArrayList<NewsItem>();
	
	public NewsMsg() {
		setMsgType(MsgType.NEWS.getFlag());
	}
	
	public NewsMsg(ReceivedMsg inMsg) {
		super(inMsg);
		setMsgType(MsgType.NEWS.getFlag());
	}
	
	public Integer getArticleCount() {
		return articles.size();
	}
	
	public List<NewsItem> getArticles() {
		return articles;
	}
	
	public void setArticles(List<NewsItem> articles) {
		if (articles != null)
			this.articles = articles;
	}
	
	public NewsMsg addNews(List<NewsItem> articles) {
		if (articles != null)
			this.articles.addAll(articles);
		return this;
	}
	
	public NewsMsg addNews(String title, String description, String picUrl, String url) {
		this.articles.add(new NewsItem(title, description, picUrl, url));
		return this;
	}
	
	public NewsMsg addNews(NewsItem news) {
		this.articles.add(news);
		return this;
	}
}
