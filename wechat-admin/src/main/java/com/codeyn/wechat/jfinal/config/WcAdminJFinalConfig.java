package com.codeyn.wechat.jfinal.config;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import com.codeyn.jfinal.config.ArpBuilder;
import com.codeyn.jfinal.config.BaseConfig;
import com.codeyn.resouce.bus.ds.DataSourceType;
import com.codeyn.utils.PackageScanner;
import com.codeyn.wechat.jfinal.interceptor.ResourceInterceptor;
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
public class WcAdminJFinalConfig extends BaseConfig {

    @Override
    protected void init() {
        // load global configuration
        loadPropertyFile("jfinal/global.properties");

        // integration spring
        initSpringCtx("classpath:spring/*.xml");

        // init freemarker configuration
        FreeMarkerRender.setProperties(loadPropertyFile("jfinal/freemarker.properties"));

        // configurate datasource & model mapping
        ArpBuilder arpBuilder = new ArpBuilder("wechat-admin", DataSourceType.DRUID);
        Properties dsProp = loadPropertyFile("jfinal/dspool.properties");
        arpBuilder.configure(null, dsProp);
        Set<String> packages = new HashSet<>();
        packages.add("com.codeyn.wechat.*.model");
        arpBuilder.mapping(packages.toArray(new String[packages.size()]));
        addArp(arpBuilder.build());
    }

    @Override
    protected void onConfigConstant(Constants me) {
        me.setBaseViewPath("/WEB-INF/pages");
        me.setDevMode(PropKit.getBoolean("devMode", false));
        me.setEncoding(Const.DEFAULT_ENCODING);

        me.setError404View("/WEB-INF/error/404.html");
        me.setError500View("/WEB-INF/error/500.html");

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
        me.setFileRenderPath("/download");

        // TODO upload smartUpload
        me.setUploadedFileSaveDirectory("/upload");
    }

    @Override
    protected void onConfigInterceptor(Interceptors me) {
        me.add(new ResourceInterceptor());
        me.add(new Tx());
    }

}
