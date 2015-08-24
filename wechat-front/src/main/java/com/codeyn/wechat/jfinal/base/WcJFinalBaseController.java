package com.codeyn.wechat.jfinal.base;

import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.codeyn.base.result.DataResult;
import com.codeyn.jfinal.base.BaseController;
import com.jfinal.render.FreeMarkerRender;

import freemarker.template.Template;

public class WcJFinalBaseController extends BaseController {

    private final static String DEFAULT_STYLE = "000000";

    public void render(String view) {
        String style = view == null ? DEFAULT_STYLE : "style";
        super.render(style + "/" + view);
    }

    public String getOpenId() {
        return (String) this.getSession().getAttribute("openId");
    }

    /**
     * 解析模板
     */
    @SuppressWarnings("unchecked")
    public String parseTemplate(String view) {

        String style = DEFAULT_STYLE;
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
