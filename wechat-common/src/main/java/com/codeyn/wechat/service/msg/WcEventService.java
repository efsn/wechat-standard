package com.codeyn.wechat.service.msg;

import java.sql.Timestamp;
import java.util.List;

import com.codeyn.wechat.wc.enums.WcEventType;
import com.codeyn.wechat.wc.enums.WcStatus;
import com.codeyn.wechat.wc.model.msg.WcEvent;
import com.jfinal.plugin.activerecord.Page;

/**
 * 微信事件
 * 
 * @author Arthur
 *
 */
public class WcEventService {

    public static Page<WcEvent> getPagationList(String tenantId, int pageNumber, int pageSize, String contentKeys) {
        return WcEvent.dao.getPagationList(tenantId, pageNumber, pageSize, contentKeys);
    }

    public static boolean deleteById(Integer id) {
        return WcEvent.dao.deleteById(id);
    }

    public static WcEvent findById(Integer id) {
        return WcEvent.dao.findById(id);
    }

    public static void save(WcEvent event) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        event.set("update_time", now);
        if (event.getInt("id") == null) {
            event.set("create_time", now);
            // 创建默认不可用
            event.set("status", WcStatus.NO.getFlag());
            event.save();
        } else {
            event.update();
        }
    }

    public static Integer changeStatus(Integer id, String tenantId) {
        return WcEvent.dao.changeStatus(id, tenantId);
    }

    /**
     * 根据key 匹配事件
     */
    public static WcEvent getEventByContentKey(String tenantId, WcEventType eventType, String contentKey) {
        return WcEvent.dao.getEventByContentKey(tenantId, eventType, contentKey);
    }

    /**
     * 根据事件类型
     */
    public static WcEvent getEventByEventType(String tenantId, WcEventType eventType) {
        return WcEvent.dao.getEventByEventType(tenantId, eventType);
    }

    /**
     * 获取一个广场的默认回复内容
     */
    public static WcEvent getDefaultMsg(String tenantId) {
        return WcEvent.dao.getDefaultMsg(tenantId);
    }

    /**
     * 根据key 匹配事件
     */
    public static List<WcEvent> getEventListByContentKey(String tenantId, WcEventType eventType, String contentKey) {
        return WcEvent.dao.getEventListByContentKey(tenantId, eventType, contentKey);
    }

    /**
     * 获取一个广场的默认回复内容
     */
    public static List<WcEvent> getEventList4Default(String tenantId) {
        return WcEvent.dao.getEventList4Default(tenantId);
    }

}
