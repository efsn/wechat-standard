package com.codeyn.wechat.jfinal.base;

import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeyn.base.jfinal.controller.BaseController;
import com.jfinal.render.FreeMarkerRender;
import com.sqq.common.result.DataResult;

import freemarker.template.Template;

public class WxJFinalBaseController extends BaseController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final static String DEFAULT_STYLE = "000000";

	
	public String getOpenId() {
		return (String) this.getSession().getAttribute("openId");
	}

	public String getMemberId() {
		return (String) this.getSession().getAttribute("memberId");
	}

	public void render(String view) {
		String style = view == null ? DEFAULT_STYLE : "style";

		super.render(style + "/" + view);
	}

	/**
	 * 解析模板
	 * 
	 * @param view
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String parseTemplate(String view) {
		// 根据广场ID获取风格-未获取到则使用默认风格
		TenantConfig tenant = TenantConfigService.getTenantConfigByTenantId(this.getTenantId());
		String style = tenant == null ? DEFAULT_STYLE : tenant.getStr("style");
		String templateUrl = "/WEB-INF/templates/" + style + "/" + view;

		// 解析模板
		StringWriter result = new StringWriter();
		try {
			Map<String, Object> root = new HashMap<String, Object>();
			for (Enumeration<String> attrs = this.getRequest().getAttributeNames(); attrs.hasMoreElements();) {
				String attrName = attrs.nextElement();
				root.put(attrName, this.getRequest().getAttribute(attrName));
			}

			Template template = FreeMarkerRender.getConfiguration().getTemplate(templateUrl);
			template.process(root, result);
		} catch (Exception ex) {
			logger.error("解析模板失败,view:" + templateUrl, ex);
		}

		return result.toString();
	}

	public void renderSucResult(String data) {
		DataResult result = new DataResult().put("data", data);
		renderJson(result);
	}

}
