package com.codeyn.wechat.pages;

import com.codeyn.base.jfinal.annos.JFinalAnnos.Route;
import com.jfinal.core.Controller;

/**
 * 微信接口服务模拟
 * 
 * @author parcel
 * 
 */
@Route(value = "/oauth2", viewPath = "/")
public class WxMockController extends Controller {

	public void access_token() {
		if ("wxac2386f873f97f70".equals(getPara("appid"))) {
			renderJson("{\"openid\":\"oVDyijr2ePsX_QIWcszp6wUmVWj4\"}");
		} else if ("wx7ea5ea5080ce5c1f".equals(getPara("appid"))) {
			renderJson("{\"openid\":\"oHqfrw9z6QxdCyg73P7fIap1uCl8\"}");
		}
	}

	
	public void authorize() {
		if (getPara("redirect_uri").contains("?")) {
			redirect(getPara("redirect_uri") + "&code=abcdefg&state=wx");
		} else {
			redirect(getPara("redirect_uri") + "?code=abcdefg&state=wx");
		}
	}
}
