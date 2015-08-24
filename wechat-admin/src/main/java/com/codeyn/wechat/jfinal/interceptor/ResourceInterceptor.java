package com.codeyn.wechat.jfinal.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class ResourceInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        Controller c = inv.getController();
        c.setAttr("res_root", "");

        c.setAttr("_referUrl", c.getRequest().getHeader("referer"));

        inv.invoke();
    }

}
