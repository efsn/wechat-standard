package com.codeyn.wechat.wc.model;

import java.util.Date;
import java.util.List;

import com.codeyn.jfinal.annos.JFinalAnnos.ModelMapping;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

/**
 * @author Arthur
 */
@ModelMapping("wc_menu")
public class WcMenu extends Model<WcMenu> implements Comparable<WcMenu> {

    private static final long serialVersionUID = 1816944586075434731L;

    public static final WcMenu me = new WcMenu();

    public WcMenu() {
        Date now = new Date();
        set("create_time", now);
        set("update_time", now);
    }

    public WcMenu(String tenantId) {
        this();
        set("tenant_id", tenantId);
    }

    public List<WcMenu> getWxMenu(String tenantId) {
        String sql = "select * from wc_menu where tenant_id = ? order by parent_id, weight asc";
        return find(sql, tenantId);
    }

    /**
     * 获取同级菜单
     */
    public List<WcMenu> getSiblingMenu(Integer id) {
        String sql = "select * from wc_menu where id in (select id from wx_menu where parent_id in (select parent_id from wx_menu where id = ?)) group by weight asc";
        return find(sql, id);
    }

    public Integer getNewestId(String tenantId) {
        String sql = "select id from wc_menu where tenant_id = ? order by weight desc";
        return Db.findFirst(sql, tenantId).getInt("id");
    }

    @Override
    public int compareTo(WcMenu menu) {
        return getInt("weight") - menu.getInt("weight");
    }

}
