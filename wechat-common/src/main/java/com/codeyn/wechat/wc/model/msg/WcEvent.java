package com.codeyn.wechat.wc.model.msg;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.codeyn.jfinal.annos.JFinalAnnos.ModelMapping;
import com.codeyn.wechat.wc.enums.WcEventType;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@ModelMapping("wx_event_config")
public class WcEvent extends Model<WcEvent> {

    private static final long serialVersionUID = 1L;

    public static final WcEvent dao = new WcEvent();

    public Page<WcEvent> getPagationList(String tenantId, int pageNumber, int pageSize, String contentKeys) {
        String select = "select * ";
        StringBuilder sqlBuilder = new StringBuilder("from wx_event_config where 1=1 ");
        List<Object> params = new ArrayList<>();
        if (StringUtils.hasText(tenantId)) {
            sqlBuilder.append(" and tenant_id=? ");
            params.add(tenantId);
        }
        if (StringUtils.hasText(contentKeys)) {
            sqlBuilder.append(" and content_keys like ? ");
            params.add("%" + contentKeys + "%");
        }
        sqlBuilder.append(" order by id desc");
        return dao.paginate(pageNumber, pageSize, select, sqlBuilder.toString(), params.toArray());
    }

    /**
     * 根据key 匹配事件
     */
    public WcEvent getEventByContentKey(String tenantId, WcEventType eventType, String contentKey) {
        String sql = "select * from wx_event_config where tenant_id=? and status=1 and event_type = ? and content_keys=? order by create_time desc";
        return WcEvent.dao.findFirst(sql, tenantId, eventType.getFlag(), "%" + contentKey + "%");
    }

    /**
     * 根据事件类型 匹配事件
     */
    public WcEvent getEventByEventType(String tenantId, WcEventType eventType) {
        String sql = "select * from wx_event_config where tenant_id=? and status=1 and event_type = ? order by create_time desc";
        return WcEvent.dao.findFirst(sql, tenantId, eventType.getFlag());
    }

    /**
     * 获取一个广场的默认回复内容
     */
    public WcEvent getDefaultMsg(String tenantId) {
        String sql = "select * from wx_event_config where tenant_id=? and status=1 and event_type = ? order by create_time desc";
        return WcEvent.dao.findFirst(sql, tenantId, WcEventType.DEFAULT.getFlag());
    }

    /**
     * 根据key 匹配事件
     */
    public List<WcEvent> getEventListByContentKey(String tenantId, WcEventType eventType, String contentKey) {
        String sql = "select * from wx_event_config where tenant_id=? and status=1 and event_type = ? and (content_keys=? or content_keys like ? or content_keys like ? or content_keys like ?) order by create_time desc";
        return WcEvent.dao.find(sql, tenantId, eventType.getFlag(), contentKey, contentKey + ";%", "%;" + contentKey
                + ";%", "%;" + contentKey);
    }

    /**
     * 获取一个广场的默认回复内容
     */
    public List<WcEvent> getEventList4Default(String tenantId) {
        String sql = "select * from wx_event_config where tenant_id=? and status=1 and event_type = ? order by create_time desc";
        return WcEvent.dao.find(sql, tenantId, WcEventType.DEFAULT.getFlag());
    }

    public Integer changeStatus(Integer id, String tenantId) {
        String sql = "update wx_event_config set status=(status+1)%2 where id = ? and tenant_id = ?";
        return Db.update(sql, id, tenantId);
    }

}
