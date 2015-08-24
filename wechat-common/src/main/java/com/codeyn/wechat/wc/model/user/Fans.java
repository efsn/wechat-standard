package com.codeyn.wechat.wc.model.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.codeyn.jfinal.annos.JFinalAnnos.ModelMapping;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@ModelMapping("wx_fans")
public class Fans extends Model<Fans> {

    private static final long serialVersionUID = 1L;

    public static final Fans dao = new Fans();

    public Page<Fans> getPagationList(String tenantId, String province, String city, String nickname, int pageNumber,
            int pageSize) {
        String select = "select * ";
        StringBuilder sqlBuilder = new StringBuilder("from wx_fans where 1=1 ");
        List<Object> params = new ArrayList<>();
        if (StringUtils.hasText(tenantId)) {
            sqlBuilder.append(" and tenant_id=? ");
            params.add(tenantId);
        }
        if (StringUtils.hasText(province)) {
            sqlBuilder.append(" and province like ? ");
            params.add("%" + province + "%");
        }
        if (StringUtils.hasText(city)) {
            sqlBuilder.append(" and city like ? ");
            params.add("%" + city + "%");
        }
        if (StringUtils.hasText(nickname)) {
            sqlBuilder.append(" and nickname like ? ");
            params.add("%" + nickname + "%");
        }
        sqlBuilder.append(" order by id desc");
        return dao.paginate(pageNumber, pageSize, select, sqlBuilder.toString(), params.toArray());
    }

}
