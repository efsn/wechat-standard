package com.codeyn.wechat.sdk.menu;

import java.util.Map;

import com.codeyn.wechat.sdk.base.WcClient;
import com.codeyn.wechat.sdk.base.model.WcBase;
import com.codeyn.wechat.sdk.base.model.WcResult;
import com.codeyn.wechat.sdk.menu.result.Menu;

/**
 * @author Arthur
 */
public class MenuClient extends WcClient{

    public MenuClient(WcBase wxBase) {
        super(wxBase);
    }

    /**
     * 查询菜单
     */
    public Menu getMenu(final String accessToken) {
        return doGet("menu", Menu.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                map.put("access_token", accessToken);
            }
            
        }, "/cgi-bin/menu/get");
    }

    /**
     * 创建菜单
     */
    public WcResult createMenu(String accessToken, final String jsonStr) {
        return doPost(WcResult.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                map.put(KEY, jsonStr);
            }
            
        }, "/cgi-bin/menu/create?access_token=" + accessToken);
    }

    /**
     * 删除菜单
     */
    public WcResult deleteMenu(final String accessToken) {
        return doGet(WcResult.class, new ParamService(){

            @Override
            public void init(Map<String, String> map) {
                map.put("access_token", accessToken);
            }
            
        }, "/cgi-bin/menu/delete");
    }
    
}
