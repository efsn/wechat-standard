package com.codeyn.wechat.sdk.user.result;

import java.util.List;

import com.codeyn.wechat.sdk.base.model.WcResult;

/**
 * 批量获取用户基本信息
 * @author Arthur
 */
public class UserInfoList extends WcResult{
    
    private List<WcUser> user_info_list;

    public List<WcUser> getUser_info_list() {
        return user_info_list;
    }

    public void setUser_info_list(List<WcUser> user_info_list) {
        this.user_info_list = user_info_list;
    }
    
}
