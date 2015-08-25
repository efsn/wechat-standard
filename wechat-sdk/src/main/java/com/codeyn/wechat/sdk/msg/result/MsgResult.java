package com.codeyn.wechat.sdk.msg.result;

import com.codeyn.wechat.sdk.base.model.WcResult;

/**
 * @author Arthur
 *
 */
public class MsgResult extends WcResult {
    
    /**
     * 群发成功后返回的消息ID
     */
    private Integer msg_id;
    
    private String msg_status;

    public Integer getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(Integer msg_id) {
        this.msg_id = msg_id;
    }

    public String getMsg_status() {
        return msg_status;
    }

    public void setMsg_status(String msg_status) {
        this.msg_status = msg_status;
    }
    
    public boolean isSuccess(){
        return msg_status == null ? super.isSuccess() : "SEND_SUCCESS".equals(msg_status);
    }
    
}
