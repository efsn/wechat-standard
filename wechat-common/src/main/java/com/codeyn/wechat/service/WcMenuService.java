package com.codeyn.wechat.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.codeyn.wechat.wc.model.WcMenu;
import com.codeyn.wechat.sdk.base.WcClientFactory;
import com.codeyn.wechat.sdk.base.model.WcResult;
import com.codeyn.wechat.sdk.menu.MenuClient;
import com.codeyn.wechat.sdk.menu.result.Menu;
import com.codeyn.wechat.wc.utils.WcCache;

/**
 * @author Arthur
 */
public class WcMenuService {

    public static void saveMenu(String tenantId, String json) {
        saveMenu(parseJson(tenantId, json));
    }

    public static boolean delete(Integer id) {
        return WcMenu.me.deleteById(id);
    }

    /**
     * 菜单拖动
     */
    public static boolean move(Integer id, Integer target, Integer origin) {
        List<WcMenu> list = WcMenu.me.getSiblingMenu(id);

        WcMenu menu = list.remove(origin - 1);
        list.set(target - 1, menu);

        for (int i = 0; i < list.size(); i++) {
            list.get(i).set("weight", i + 1).update();
        }
        return true;
    }

    /**
     * 一个菜单
     */
    private static WcMenu parseJson(String tenantId, String json) {
        WcMenu menu = new WcMenu(tenantId);
        JSONObject obj = JSON.parseObject(json);

        // TODO 判断
        menu.set("id", obj.getInteger("id"));
        menu.set("parent_id", obj.getInteger("parentId"));
        menu.set("weight", obj.getInteger("weight"));
        menu.set("title", obj.getString("title"));
        menu.set("type", obj.getString("type"));
        menu.set("content_type", obj.getString("contentType"));
        menu.set("value", obj.getString("value"));
        menu.set("htmlTag", obj.getString("html_tag"));

        return menu;
    }

    /**
     * 保存菜单配置
     */
    public static void saveMenu(WcMenu... menus) {
        if (menus != null && menus.length > 0) {
            for (WcMenu menu : menus) {
                if (menu.getInt("id") != null) {
                    menu.update();
                } else {
                    menu.save();
                }
            }
        }
    }

    /**
     * 发布菜单到微信
     */
    public static WcResult publishMenu(String tenantId) {
        String json = getMenuJson(WcMenu.me.getWxMenu(tenantId));
        String accessToken = WcCache.getAccessToken(tenantId);
        WcResult rs = WcClientFactory.getClient(MenuClient.class, WcCache.getWxBase(tenantId)).createMenu(accessToken,
                json);

        // 回调校验accessToken是否有效
        WcCache.refreshAccessTokenIfInvalid(rs, tenantId);
        return rs;
    }

    /**
     * 获取微信服务器菜单配置
     */
    public static void syncMenus(String tenantId) {
        // 获取微信端菜单配置

        String accessToken = WcCache.getAccessToken(tenantId);
        Menu rs = WcClientFactory.getClient(MenuClient.class, WcCache.getWxBase(tenantId)).getMenu(accessToken);

        // 回调校验accessToken是否有效
        WcCache.refreshAccessTokenIfInvalid(rs, tenantId);

        // 解析json & save
        List<WcMenu> list = parseMenu(rs, tenantId);
        saveMenu(list.toArray(new WcMenu[0]));
    }

    public static WcMenu[][] getMenus(String tenantId) {
        return getMenus(WcMenu.me.getWxMenu(tenantId));
    }

    /**
     * 将所有菜单按权重排序
     */
    private static WcMenu[][] getMenus(List<WcMenu> list) {
        if (list == null || list.size() < 1) return null;
        Map<WcMenu, List<WcMenu>> map = new HashMap<>();
        List<WcMenu> parents = new ArrayList<>();

        for (WcMenu menu : list) {
            Integer parentId = menu.getInt("parent_id");
            if (parentId == null) {
                parents.add(menu);
            } else {
                List<WcMenu> subs = map.get(menu);
                if (subs == null) {
                    subs = new ArrayList<>();
                }
                subs.add(menu);
            }
        }

        WcMenu[][] menus = new WcMenu[parents.size()][];

        // 按权重排序
        Collections.sort(parents);
        for (int i = 0; i < parents.size(); i++) {
            List<WcMenu> subs = map.get(parents.get(i));
            Collections.sort(subs);
            subs.add(0, parents.get(i));
            menus[i] = subs.toArray(new WcMenu[0]);
        }
        return menus;
    }

    /**
     * 创建菜单JSON
     */
    private static String getMenuJson(WcMenu[][] menus) {
        if (menus == null || menus.length < 1) return null;

        JSONObject json = new JSONObject();
        JSONArray buttons = new JSONArray();

        for (WcMenu[] subs : menus) {
            JSONObject button = new JSONObject();
            button.put("name", subs[0]);

            if (subs.length > 1) {
                // 二级子菜单
                JSONArray arr = new JSONArray();
                for (int i = 1; i < subs.length; i++) {
                    WcMenu sub = subs[i];
                    JSONObject subJson = new JSONObject();
                    subJson.put("name", sub.getStr("title"));
                    subJson.put("type", sub.getStr("type"));
                    subJson.put(getFieldNameByType(sub.getStr("type")), sub.getStr("value"));
                    arr.add(subJson);
                }
                button.put("sub_button", arr);
            } else {
                // 一级菜单
                button.put("type", subs[0].getStr("type"));
                button.put(getFieldNameByType(subs[0].getStr("type")), subs[0].getStr("value"));
            }
            buttons.add(button);
        }
        json.put("button", buttons);
        return json.toJSONString();
    }

    private static String getMenuJson(List<WcMenu> menus) {
        return getMenuJson(getMenus(menus));
    }

    /**
     * 远程同步菜单 TODO html_tag
     */
    private static List<WcMenu> parseMenu(Menu rs, String tenantId) {
        if (rs == null || rs.getButton().isEmpty()) return null;
        List<WcMenu> list = new ArrayList<>();
        // 一级菜单
        List<Menu> parents = rs.getButton();

        for (int i = 0; i < parents.size(); i++) {
            Menu menu = parents.get(i);
            WcMenu m = new WcMenu(tenantId);
            m.set("weight", i + 1);
            m.set("title", menu.getName());

            List<Menu> subButton = menu.getSub_button();

            if (subButton == null || subButton.isEmpty()) {
                m.set("type", menu.getType());
                m.set("value", menu.getValue());
                list.add(m);
            } else {
                saveMenu(m);
                Integer parentId = WcMenu.me.getNewestId(tenantId);

                for (int j = 0; j < subButton.size(); j++) {
                    WcMenu sm = new WcMenu(tenantId);
                    sm.set("parent_id", parentId);
                    sm.set("weight", j + 1);
                    sm.set("title", subButton.get(j).getName());
                    sm.set("type", subButton.get(j).getType());
                    sm.set("value", subButton.get(j).getValue());
                    list.add(sm);
                }
            }
        }
        return list;
    }

    /**
     * 根据类型获取json中的属性name
     */
    private static String getFieldNameByType(String type) {
        switch (type) {
            case "view":
                return "url";

            case "click":
                return "key";

            case "media_id":
            case "view_limited":
                return "media_id";

            default:
                return null;
        }
    }

}
