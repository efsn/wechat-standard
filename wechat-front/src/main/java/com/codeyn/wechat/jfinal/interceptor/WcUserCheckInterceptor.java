package com.codeyn.wechat.jfinal.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeyn.base.exception.DefaultStatus;
import com.codeyn.base.result.ResultHelper;
import com.codeyn.wechat.jfinal.base.WcBaseController;
import com.codeyn.wechat.jfinal.interceptor.AuthenticationInterceptor.VerifyLogin;
import com.codeyn.wechat.system.model.WcConfig;
import com.codeyn.wechat.system.service.WcConfigService;
import com.codeyn.wechat.utils.FrontUtil;
import com.codeyn.wechat.utils.WcUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * 微信用户检测拦截器
 * 
 * @author Arthur
 * 
 */
public class WcUserCheckInterceptor implements Interceptor {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void intercept(Invocation ai) {
		Method actionMethod = ai.getMethod();

		// 判断action是否需要获取openId
		if (!actionMethod.isAnnotationPresent(VerifyWxBrowser.class)
				&& !actionMethod.isAnnotationPresent(VerifyLogin.class)) {
			ai.invoke();
			return;
		}

		// 验证用户是否已经获取到openId
		WcBaseController wxJFinalBaseController = (WcBaseController) ai.getController();
		if (wxJFinalBaseController.getOpenId() != null) {
			ai.invoke();
			return;
		}

		// 异步请求返回错误报文-无法通过重定向获取openId
		if (FrontUtil.isAjax(wxJFinalBaseController.getRequest())) {
			DefaultStatus defaultStatus = new DefaultStatus(801, "用户未登录");
			wxJFinalBaseController.renderJson(ResultHelper.failResult(defaultStatus));
			return;
		}

		// 从微信服务器获取当前广场openId
		WcConfig wxConfig = WcConfigService.getConfigByTenantId("");
		if (StringUtils.isBlank(wxJFinalBaseController.getPara("code"))) {

			// 拼装认证报文-发送获取code重定向请求
			String redirectUrl = WcUtil.encaAuthorizeUrl(wxConfig.getStr("app_id"), wxJFinalBaseController);
			wxJFinalBaseController.redirect(redirectUrl.toString());
			return;
		}

		// 获取到code后从微信服务器获取openId
		String openId = WcUtil.getOpenId(wxConfig, wxJFinalBaseController.getPara("code"));
		
		// 将openId放入session
        wxJFinalBaseController.getSession().setAttribute("openId", openId);

		// 获取当前广场openId对应的用户
//		WxUser wxUser = ;

		// 若会员已存在，则自动登陆用户
//		if (wxUser == null) {
//		    wxJFinalBaseController.redirect("/toRegister?sourceUrl=" + WcUtil.getSourceUrl(wxJFinalBaseController));
//		    return;
//		}
//		
//		wxJFinalBaseController.getSession().setAttribute("memberId", wxUser.getStr("member_id"));
		ai.invoke();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface VerifyWxBrowser {
	}
}
