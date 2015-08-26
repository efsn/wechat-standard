package com.codeyn.wechat.sdk.msg;

import java.util.HashMap;
import java.util.Map;

import com.codeyn.base.result.DataResult;
import com.codeyn.wechat.sdk.base.SysBase;
import com.codeyn.wechat.sdk.base.WcApiClient;

/**
 * 微信对外服务
 * @author Codeyn
 *
 */
public class WcMsgClient extends WcApiClient {

	public WcMsgClient(SysBase base) {
		super(base);
	}

	/**
	 * 模版消息
	 */
	public DataResult sendMsg(final String memberId, final Integer msgType, final Map<String, Object> params) {
		return doService(DataResult.class, new ParamService() {
			@Override
			public void init(Map<String, Object> map) {
				map.put("memberId", memberId);
				map.put("msgType", msgType);
				map.putAll(params);
			}
		}, "/msg/sendTemplateMsg.json");
	}

	public static void main(String args[]) {
        SysBase appBase = new SysBase("http://localhost:8080", "xx");
        WcMsgClient wxMsgClient = new WcMsgClient(appBase);

        Map<String, Object> params1 = new HashMap<String, Object>();
        params1.put("first", "您的积分账户变更如下");
        params1.put("FieldName", "账号");
        params1.put("Account", "12345");
        params1.put("change", "增加");
        params1.put("CreditChange", "34");
        params1.put("CreditTotal", "5623");
        params1.put("Remark", "您可以在网站或手机APP使用积分下单抵现，10积分=1元。");
        wxMsgClient.sendMsg("215187000001", 1, params1);
        
        Map<String, Object> params2 = new HashMap<String, Object>();
        params2.put("first", "恭喜您获得1张京东券，面值20元（转增自XXX）");
        params2.put("orderTicketStore", "网购非虚拟商品店铺");
        params2.put("orderTicketRule", "每笔订单满159元即可使用本券");
        params2.put("remark", "京东券可在购物、支付时抵扣等额现金（不含虚拟商品）");
        wxMsgClient.sendMsg("215187000001", 2, params2);
        
        Map<String, Object> params3 = new HashMap<String, Object>();
        params3.put("first", "尊敬的顾客，最新交易提醒");
        params3.put("time", "2015年7月10日 下午3时49分");
        params3.put("org", "KFC");
        params3.put("type", "餐饮");
        params3.put("money", "34");
        params3.put("point", "5");
        params3.put("remark", "您可以在网站或手机APP使用积分下单抵现，10积分=1元。");
        wxMsgClient.sendMsg("215187000001", 3, params3);
        
        Map<String, Object> params4 = new HashMap<String, Object>();
        params4.put("first", "您有1张京东券将于12小时后过期，面值20元");
        params4.put("orderTicketStore", "网购非虚拟商品店铺");
        params4.put("orderTicketRule", "每笔订单满159元即可使用本券");
        params4.put("remark", "京东券可在购物、支付时抵扣等额现金（不含虚拟商品）");
        wxMsgClient.sendMsg("215187000001", 4, params4);
        
        Map<String, Object> params5 = new HashMap<String, Object>();
        params5.put("first", "您已成功使用优惠券");
        params5.put("keyword1", "会员开卡礼满100减20");
        params5.put("keyword2", "辛香汇漕河泾店");
        params5.put("keyword3", "2015年7月10日 下午3时49分");
        params5.put("remark", "感谢您的支持");
        wxMsgClient.sendMsg("215187000001", 5, params5);
        
        Map<String, Object> params6 = new HashMap<String, Object>();
        params6.put("first", "活动报名成功");
        params6.put("keynote1", "2015年7月10日 下午3时49分");
        params6.put("keynote2", "欢乐迪大聚会");
        params6.put("remark", "请关注详情了解更多");
        wxMsgClient.sendMsg("215187000001", 6, params6);
        
    }
}
