package com.codeyn.wechat.sdk.user.result;

import java.util.List;

import com.codeyn.wechat.sdk.base.model.WxResult;

/**
 * 批量获取用户基本信息
 * @author Arthur
 */
public class UserInfoList extends WxResult{
    
    private List<WxUser> user_info_list;

    public List<WxUser> getUser_info_list() {
        return user_info_list;
    }

    public void setUser_info_list(List<WxUser> user_info_list) {
        this.user_info_list = user_info_list;
    }
    
}
