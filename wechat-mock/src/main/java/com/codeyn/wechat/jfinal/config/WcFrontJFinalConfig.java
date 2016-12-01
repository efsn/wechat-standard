package com.codeyn.wechat.jfinal.config;

import java.util.HashSet;
import java.util.Set;

import com.codeyn.base.util.PackageScanner;
import com.codeyn.jfinal.config.BaseConfig;
import com.jfinal.config.Constants;
import com.jfinal.config.Interceptors;
import com.jfinal.core.Const;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.render.FreeMarkerRender;

import freemarker.template.TemplateHashModel;

/**
 * Base framework configuration
 * 
 * @author Codeyn
 *
 */
public class WcFrontJFinalConfig extends BaseConfig {

    @Override
    protected void init() {
        // load global configuration
        loadPropertyFile("jfinal/global.properties");

        // init freemarker configuration
        FreeMarkerRender.setProperties(loadPropertyFile("jfinal/freemarker.properties"));

        // configurate datasource & model mapping
        loadPropertyFile("jfinal/datasource.properties");
    }

    @Override
    protected void onConfigConstant(Constants me) {
        me.setBaseViewPath("/WEB-INF/pages");
        me.setDevMode(PropKit.getBoolean("devMode", false));
        me.setEncoding(Const.DEFAULT_ENCODING);

        me.setError404View("/WEB-INF/errors/404.html");
        me.setError500View("/WEB-INF/errors/500.html");

        // freemarker add static template
        Set<String> packages = new HashSet<>();
        packages.add("com.codeyn.wechat.*.enums");
        packages.add("com.codeyn.wechat.utils");
        packages.add("com.codeyn.wechat.jfinal.bridger");

        Set<Class<?>> clazzes = PackageScanner.scanPackage(packages.toArray(new String[packages.size()]));
        for (Class<?> clazz : clazzes) {
            TemplateHashModel tmp = buildStaticTemplate(clazz.getName());
            FreeMarkerRender.getConfiguration().setSharedVariable(clazz.getSimpleName(), tmp);
        }

        // TODO download
        me.setBaseDownloadPath("/download");

        // TODO upload smartUpload
        me.setBaseUploadPath("/upload");
    }

    @Override
    protected void onConfigInterceptor(Interceptors me) {
        me.add(new Tx());
    }

}
