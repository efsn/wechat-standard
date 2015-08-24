package com.codeyn.wechat.system.service;

import java.sql.Timestamp;

import com.codeyn.jfinal.base.PageBean;
import com.codeyn.wechat.system.model.WcConfig;
import com.jfinal.plugin.activerecord.Page;

/**
 * 微信公众号管理
 * 
 * @author Arthur
 *
 */
public class WcConfigService {

    public static void save(WcConfig wx) {
        Integer id = wx.getInt("id");
        Timestamp time = new Timestamp(System.currentTimeMillis());
        wx.set("update_time", time);
        if (id == null) {
            wx.set("create_time", time);
            wx.save();
        } else {
            wx.update();
        }
    }

    /**
     * 通过广场ID查询信息
     */
    public static WcConfig getConfigByWxNo(String wxNo) {
        return WcConfig.dao.getConfigByWxNo(wxNo);
    }

    public static WcConfig getConfigByUnique(String tenantId, String wxNo) {
        return WcConfig.dao.getConfigByUnique(tenantId, wxNo);
    }

    public static WcConfig getConfigByTenantId(String tenantId) {
        return WcConfig.dao.getConfigByTenantId(tenantId);
    }

    public static WcConfig getConfigById(Integer id) {
        return WcConfig.dao.findById(id);
    }

    public static void delete(Integer id) {
        WcConfig.dao.deleteById(id);
    }

    /**
     * 分页List
     */
    public static Page<WcConfig> getConfigList(PageBean<WcConfig> pageBean) {
        return WcConfig.dao.getConfigList(pageBean);
    }
}
