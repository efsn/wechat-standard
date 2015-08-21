package com.codeyn.wechat.jfinal.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeyn.wechat.jfinal.base.WxJFinalBaseController;
import com.codeyn.wechat.utils.FrontUtil;
import com.codeyn.wechat.utils.WeixinUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.sqq.common.exception.DefaultStatus;
import com.sqq.common.result.ResultHelper;

/**
 * 登录认证拦截器
 * 
 * @author parcel
 * 
 */
public class AuthenticationInterceptor implements Interceptor {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void intercept(Invocation ai) {
		Method actionMethod = ai.getMethod();
		WxJFinalBaseController wxJFinalBaseController = (WxJFinalBaseController) ai.getController();

		// 判断action是否需要验证用户是否登录
		if (!actionMethod.isAnnotationPresent(VerifyLogin.class)) {
			ai.invoke();
			return;
		}

		// 验证用户是否已登录
		if (wxJFinalBaseController.getMemberId() != null) {
			ai.invoke();
			return;
		}

		// 页面跳转-异步请求返回错误报文|重定向到注册
		if (FrontUtil.isAjax(wxJFinalBaseController.getRequest())) {
			DefaultStatus defaultStatus = new DefaultStatus(801, "用户未登录");
			wxJFinalBaseController.renderJson(ResultHelper.failResult(defaultStatus));
		} else {
			wxJFinalBaseController.redirect("/toRegister?sourceUrl=" + WeixinUtil.getSourceUrl(wxJFinalBaseController));
		}
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface VerifyLogin {
	}
}
