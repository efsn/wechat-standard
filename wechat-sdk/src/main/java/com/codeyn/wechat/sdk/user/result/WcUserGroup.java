package com.codeyn.wechat.sdk.user.result;

import java.util.List;

import com.codeyn.wechat.sdk.base.model.WcResult;

public class WcUserGroup extends WcResult{

    /**
     * 所有分组
     */
    private List<WcUserGroup> groups;
    
    /**
     * 用户所属的groupid
     */
    private String groupid;
    
    
    // -------------------------------------------------------------------
    
    /**
     * 分组id，由微信分配
     */
    private Integer id;
    
    /**
     * 分组名字，UTF8编码
     */
    private String name;
    
    /**
     * 分组内用户数量
     */
    private Integer count;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<WcUserGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<WcUserGroup> groups) {
        this.groups = groups;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }
    
}
