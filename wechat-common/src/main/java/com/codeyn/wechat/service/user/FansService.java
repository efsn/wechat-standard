package com.codeyn.wechat.service.user;

import com.codeyn.wechat.wc.model.user.Fans;
import com.jfinal.plugin.activerecord.Page;
import com.codeyn.wechat.sdk.base.WcClientFactory;
import com.codeyn.wechat.sdk.user.UserClient;
import com.codeyn.wechat.sdk.user.result.WcUser;
import com.codeyn.wechat.wc.utils.WcCache;

public class FansService {

    public static Page<Fans> getPagationList(String tenantId, String province, String city, String nickname,
            int pageNumber, int pageSize) {
        return Fans.dao.getPagationList(tenantId, province, city, nickname, pageNumber, pageSize);
    }

    public static void save(String tenantId, String openId) {
        UserClient client = WcClientFactory.getClient(UserClient.class, WcCache.getWxBase(tenantId));
        WcUser wxUser = client.getUserInfo(WcCache.getAccessToken(tenantId), openId, "zh_CN");

        // 回调校验accessToken是否有效
        WcCache.refreshAccessTokenIfInvalid(wxUser, tenantId);

        Fans fan = new Fans();
        fan.set("id", wxUser.getOpenid());
        fan.set("nickname", wxUser.getNickname());
        fan.set("sex", wxUser.getSex());
        fan.set("language", wxUser.getLanguage());
        fan.set("city", wxUser.getCity());
        fan.set("province", wxUser.getProvince());
        fan.set("country", wxUser.getCountry());
        fan.set("headimgurl", wxUser.getHeadimgurl());
        fan.set("subscribe_time", wxUser.getSubscribe_time() * 1000);
        fan.set("subscribe", wxUser.getSubscribe());
        fan.set("tenant_id", tenantId);
        fan.set("wx_no", WcCache.getWxBase(tenantId).getWxNo());

        boolean flag = fan.update();
        if (!flag) {
            fan.save();
        }
    }

    /**
     * 保存或更新
     */
    public static void save(Fans fans) {
        boolean flag = fans.update();
        if (flag) {
            fans.save();
        }
    }

    /**
     * 取消关注
     */
    public static void unUubscribe(String openId) {
        Fans fans = new Fans();
        fans.set("id", openId);
        fans.set("subscribe", 0);
        fans.update();
    }

    /**
     * 根据openId 获取用户信息
     */
    public static Fans getFans(String openId) {
        return Fans.dao.findById(openId);
    }

}
