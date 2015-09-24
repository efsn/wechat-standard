package com.codeyn.wechat.jfinal.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeyn.wechat.jfinal.base.MsgController;
import com.codeyn.wechat.sdk.utils.SignatureCheckUtil;
import com.codeyn.wechat.wc.utils.WcCache;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;

/**
 * Msg 拦截器
 * 
 * 1：响应开发者中心服务器配置 URL
 * 2：签名检测
 * 
 * 注意： MsgController 的继承类如果覆盖了 index 方法，则需要对该 index 方法声明该拦截器
 * 		  因为子类覆盖父类方法会使父类方法配置的拦截器失效，从而失去本拦截器的功能
 */
public class MsgInterceptor implements Interceptor {
	
	private static final Logger logger =  LoggerFactory.getLogger(MsgInterceptor.class);
	
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		if (!(controller instanceof MsgController))
			throw new RuntimeException("控制器需要继承 MsgController");
		
		// 如果是服务器配置请求，则配置服务器并返回
		if (isConfigServerRequest(controller)) {
			configServer(controller);
			return ;
		}
		
		// 签名检测
		if (checkSignature(controller)) {
			inv.invoke();
		} else {
			controller.renderText("签名验证失败，请确定是微信服务器在发送消息过来");
		}
	}
	
	/**
	 * 检测签名
	 */
	private boolean checkSignature(Controller c) {
		String signature = c.getPara("signature");
		String timestamp = c.getPara("timestamp");
		String nonce = c.getPara("nonce");
		
		if (StrKit.isBlank(signature) || StrKit.isBlank(timestamp) || StrKit.isBlank(nonce)) {
			c.renderText("check signature failure");
			return false;
		}
		
		if (SignatureCheckUtil.checkSignature(WcCache.getWxBase("").getToken(), signature, timestamp, nonce)) {
			return true;
		} else {
			logger.error("check signature failure: " +
					" signature = " + c.getPara("signature") +
					" timestamp = " + c.getPara("timestamp") +
					" nonce = " + c.getPara("nonce"));
			
			return false;
		}
	}
	
	/**
	 * 是否为开发者中心保存服务器配置的请求
	 */
	private boolean isConfigServerRequest(Controller controller) {
		return StrKit.notBlank(controller.getPara("echostr"));
	}
	
	/**
	 * 配置开发者中心微信服务器所需的 url 与 token
	 * true 为config server 请求，false 正式消息交互请求
	 */
	public void configServer(Controller c) {
		// 通过 echostr 判断请求是否为配置微信服务器回调所需的 url 与 token
		String echostr = c.getPara("echostr");
		String signature = c.getPara("signature");
        String timestamp = c.getPara("timestamp");
        String nonce = c.getPara("nonce");
        
		boolean isOk = SignatureCheckUtil.checkSignature(WcCache.getWxBase("").getToken(), signature, timestamp, nonce);
		if (isOk)
			c.renderText(echostr);
		else
			logger.error("验证失败：configServer");
	}
	
}
