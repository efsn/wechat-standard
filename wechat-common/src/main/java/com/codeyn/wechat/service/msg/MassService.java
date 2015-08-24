package com.codeyn.wechat.service.msg;

import java.util.HashMap;
import java.util.Map;

import com.codeyn.wechat.sdk.base.WcClientFactory;
import com.codeyn.wechat.sdk.base.model.WcResult;
import com.codeyn.wechat.sdk.material.result.Media;
import com.codeyn.wechat.sdk.msg.MassClient;
import com.codeyn.wechat.service.material.NewsService;
import com.codeyn.wechat.wc.utils.WcCache;

/**
 * 群发
 * 
 * @author Arthur
 *
 */
public class MassService {

    public static final String MASS_MPNEWS = "mpnews";
    public static final String MASS_TEXT = "text";
    public static final String MASS_VOICE = "voice";
    public static final String MASS_MUSIC = "thumb";
    public static final String MASS_IMAGE = "image";
    public static final String MASS_VIDEO = "video";
    public static final String MASS_WXCARD = "wxcard";

    /**
     * 群发预览
     */
    public static WcResult previewMsg(String tenantId, String openId, Integer newsId) {
        Media media = NewsService.uploadNews(tenantId, newsId);

        Map<String, Object> data = new HashMap<>();
        data.put("media_id", media.getMedia_id());
        MassClient client = WcClientFactory.getClient(MassClient.class, WcCache.getWxBase(tenantId));
        WcResult rs = client.preview(WcCache.getAccessToken(tenantId), openId, null, MASS_MPNEWS, data);

        // 回调校验accessToken是否有效
        WcCache.refreshAccessTokenIfInvalid(rs, tenantId);
        return rs;
    }

}
