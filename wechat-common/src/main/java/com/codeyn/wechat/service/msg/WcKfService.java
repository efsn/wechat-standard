package com.codeyn.wechat.service.msg;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import com.codeyn.base.exception.BusinessException;
import com.codeyn.jfinal.base.PageBean;
import com.codeyn.wechat.sdk.base.WcClientFactory;
import com.codeyn.wechat.sdk.base.model.WcResult;
import com.codeyn.wechat.sdk.msg.CustomerServiceClient;
import com.codeyn.wechat.sdk.msg.result.CustomerService;
import com.codeyn.wechat.wc.utils.WcCache;

/**
 * 客服
 * 
 * @author Arthur
 *
 */
public class WcKfService {

    private static CustomerServiceClient getClient(String tenantId) {
        return WcClientFactory.getClient(CustomerServiceClient.class, WcCache.getWxBase(tenantId));
    }

    /**
     * 最多只能添加10个客服
     */
    public static PageBean<CustomerService> getPagationList(String tenantId, boolean online) {
        CustomerServiceClient client = getClient(tenantId);
        CustomerService result = client.getAccountList(online, WcCache.getAccessToken(tenantId));

        // 回调校验accessToken是否有效
        WcCache.refreshAccessTokenIfInvalid(result, tenantId);

        if (result == null || !result.isSuccess()) {
            String errorMsg = result == null ? "系统内部异常" : "调用微信接口出错：错误原因：" + result.getErrmsg();
            throw new BusinessException(errorMsg);
        }

        List<CustomerService> list = online ? new ArrayList<CustomerService>() : result.getKf_list();

        PageBean<CustomerService> page = new PageBean<>();
        page.setPageNo(1);
        page.setPageSize(list.size());
        page.setList(list);
        page.setTotalRow(list.size());

        return page;
    }

    public static WcResult deleteKefu(String tenantId, String kf_account) {
        CustomerServiceClient client = getClient(tenantId);
        WcResult result = client.deleteAccount(WcCache.getAccessToken(tenantId), kf_account);

        // 回调校验accessToken是否有效
        WcCache.refreshAccessTokenIfInvalid(result, tenantId);
        return result;
    }

    public static WcResult saveKefu(boolean isNew, String tenantId, String account, String name, String password) {
        CustomerServiceClient client = getClient(tenantId);
        WcResult result = client.saveAccount(WcCache.getAccessToken(tenantId), isNew, account, name,
                DigestUtils.md5Hex(password));

        // 回调校验accessToken是否有效
        WcCache.refreshAccessTokenIfInvalid(result, tenantId);
        return result;
    }

}
