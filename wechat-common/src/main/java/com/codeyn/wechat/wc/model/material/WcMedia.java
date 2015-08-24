package com.codeyn.wechat.wc.model.material;

import java.util.Date;
import java.util.List;

import com.codeyn.jfinal.annos.JFinalAnnos.ModelMapping;
import com.codeyn.wechat.sdk.material.enums.MediaType;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

/**
 * @author Arthur
 */
@ModelMapping("wc_media")
public class WcMedia extends Model<WcMedia> {

    private static final long serialVersionUID = -9062454583104054955L;

    public static final WcMedia me = new WcMedia();

    /**
     * 根据id获取媒体对象
     */
    public WcMedia getMedia(String tenantId, String mediaId) {
        String sql = "select * from wc_media where tenant_id = ? and media_id = ?";
        return findFirst(sql, tenantId, mediaId);
    }

    public Integer delete(String tenantId, String mediaId) {
        String sql = "delete from wc_wc_media where tenant_id = ? and media_id = ?";
        return Db.update(sql, tenantId, mediaId);
    }

    /**
     * 获取未过期的媒体列表
     */
    public List<WcMedia> getHistoryMedia(String tenantId, MediaType type) {
        String sql = "select * from wc_media where tenant_id = ? and type = ? and ( expires_time is null or expires_time > ? )";
        return find(sql, tenantId, type.getFlag(), new Date());
    }

    public boolean isAvailable() {
        Date date = getDate("expires_time");
        return date == null || new Date().before(date);
    }

}
