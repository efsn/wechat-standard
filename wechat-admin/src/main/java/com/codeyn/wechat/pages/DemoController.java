package com.codeyn.wechat.pages;

import com.codeyn.jfinal.annos.JFinalAnnos.Route;
import com.codeyn.wechat.jfinal.base.WcBaseController;

@Route("/demo")
public class DemoController extends WcBaseController {

    public void index() {
        renderText("Hello World");
    }

}
