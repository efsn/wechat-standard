package com.codeyn.wechat.system.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.codeyn.jfinal.annos.JFinalAnnos.ModelMapping;
import com.codeyn.jfinal.base.PageBean;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 微信配置
 * 
 * @author Arthur
 *
 */
@ModelMapping("sys_wc_config")
public class WcConfig extends Model<WcConfig> {

    private static final long serialVersionUID = 1L;

    public static final WcConfig dao = new WcConfig();

    public WcConfig getConfigByWxNo(String wxNo) {
        String sql = "SELECT * FROM SYS_WX_CONFIG WHERE WX_NO = ?";
        return dao.findFirst(sql, wxNo);
    }

    public WcConfig getConfigByUnique(String tenantId, String wxNo) {
        String sql = "SELECT * FROM SYS_WX_CONFIG WHERE TENANT_ID = ? OR WX_NO = ?";
        return dao.findFirst(sql, tenantId, wxNo);
    }

    public WcConfig getConfigByTenantId(String tenantId) {
        String sql = "SELECT * FROM SYS_WX_CONFIG WHERE TENANT_ID = ?";
        return dao.findFirst(sql, tenantId);
    }

    public Page<WcConfig> getConfigList(PageBean<WcConfig> pageBean) {
        String select = "SELECT * ";
        StringBuffer sqlBuilder = new StringBuffer(" FROM SYS_WX_CONFIG WHERE 1=1");

        List<Object> params = new ArrayList<>();
        if (StringUtils.isNotBlank(pageBean.getExt().get("tenant_id"))) {
            sqlBuilder.append(" AND TENANT_ID = ?");
            params.add(pageBean.getExt().get("tenant_id"));
        }
        if (StringUtils.isNotBlank(pageBean.getExt().get("wx_no"))) {
            sqlBuilder.append(" AND WX_NO LIKE ?");
            params.add("%" + pageBean.getExt().get("wx_no") + "%");
        }
        if (StringUtils.isNotBlank(pageBean.getExt().get("name"))) {
            sqlBuilder.append(" AND NAME LIKE ?");
            params.add("%" + pageBean.getExt().get("name") + "%");
        }
        sqlBuilder.append(" ORDER BY UPDATE_TIME DESC ");
        return dao.paginate(pageBean.getPageNo(), pageBean.getPageSize(), select, sqlBuilder.toString(),
                params.toArray());
    }

}
