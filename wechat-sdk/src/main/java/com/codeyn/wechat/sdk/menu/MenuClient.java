package com.codeyn.wechat.sdk.menu;

import java.util.Map;

import com.codeyn.wechat.sdk.base.WxClient;
import com.codeyn.wechat.sdk.base.model.WxBase;
import com.codeyn.wechat.sdk.base.model.WxResult;
import com.codeyn.wechat.sdk.menu.result.Menu;

/**
 * @author Arthur
 */
public class MenuClient extends WxClient{

    public MenuClient(WxBase wxBase) {
        super(wxBase);
    }

    /**
     * 查询菜单
     */
    public Menu getMenu(final String accessToken) {
        return doGet(Menu.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                map.put("access_token", accessToken);
            }
            
        }, "/cgi-bin/menu/get");
    }

    /**
     * 创建菜单
     */
    public WxResult createMenu(String accessToken, final String jsonStr) {
        return doPost(WxResult.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                map.put(KEY, jsonStr);
            }
            
        }, "/cgi-bin/menu/create?access_token=" + accessToken);
    }

    /**
     * 删除菜单
     */
    public WxResult deleteMenu(final String accessToken) {
        return doGet(WxResult.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                map.put("access_token", accessToken);
            }
            
        }, "/cgi-bin/menu/delete");
    }
    
}
