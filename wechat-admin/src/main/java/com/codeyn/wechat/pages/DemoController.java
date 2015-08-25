package com.codeyn.wechat.pages;

import com.codeyn.jfinal.annos.JFinalAnnos.Route;
import com.codeyn.wechat.jfinal.base.WcJFinalBaseController;

@Route("/demo")
public class DemoController extends WcJFinalBaseController {

    public void index() {
        renderText("Hello World");
    }

}
