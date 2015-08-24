package com.codeyn.wechat.jfinal.config;

import com.codeyn.jfinal.config.BaseConfig;
import com.jfinal.config.Constants;
import com.jfinal.render.ViewType;

/**
 * Base framework configuration
 * 
 * @author Arthur
 * @version 1.0
 *
 */
public class WcAdminJFinalConfig extends BaseConfig {

    @Override
    protected void init() {
        loadPropertyFile("global.properties");

        loadPropertyFile("datasource.properties");

    }

    @Override
    protected void onConfigConstant(Constants me) {
        me.setViewType(ViewType.FREE_MARKER);

        // TODO why sub from web-inf & interceptor all static sources
        me.setBaseViewPath("/WEB-INF/template/x");

        // TODO global interceptor exception
        me.setError404View("/WEB-INF/error/404.html");
        me.setError500View("/WEB-INF/error/500.html");

        // TODO download
        me.setFileRenderPath("/download");

        // TODO upload smartUpload
        me.setUploadedFileSaveDirectory("/upload");
    }

}
