package com.codeyn.wechat.jfinal.base;

import org.apache.commons.lang.StringUtils;

import com.codeyn.wechat.jfinal.interceptor.MsgInterceptor;
import com.codeyn.wechat.sdk.base.model.WcBase;
import com.codeyn.wechat.sdk.msg.enums.EventType;
import com.codeyn.wechat.sdk.msg.enums.MsgType;
import com.codeyn.wechat.sdk.msg.model.receive.EventMsg;
import com.codeyn.wechat.sdk.msg.model.receive.NormalMsg;
import com.codeyn.wechat.sdk.msg.model.receive.ReceivedMsg;
import com.codeyn.wechat.sdk.msg.model.send.SentMsg;
import com.codeyn.wechat.sdk.msg.utils.MsgReceivedParser;
import com.codeyn.wechat.sdk.msg.utils.MsgSentBuilder;
import com.codeyn.wechat.sdk.utils.MsgEncryptUtil;
import com.codeyn.wechat.wc.utils.WcCache;
import com.jfinal.aop.Before;
import com.jfinal.core.JFinal;
import com.jfinal.kit.HttpKit;

/**
 * 接收微信服务器消息，自动解析成 InMsg 并分发到相应的处理方法
 */
public abstract class MsgController extends WcBaseController {

    /**
     * weixin 公众号服务器调用唯一入口，即在开发者中心输入的 URL 必须要指向此 action
     */
    @Before(MsgInterceptor.class)
    public void index() {
        String inMsg = getReceivedXmlMsg();

        // 开发模式输出微信服务发送过来的 xml 消息
        if (JFinal.me().getConstants().getDevMode()) {
            System.out.println("接收消息:");
            System.out.println(inMsg);
        }

        // 解析消息并根据消息类型分发到相应的处理方法
        ReceivedMsg msg = getReceiveMsg(inMsg);
        processReceivedMsg(msg);
    }

    /**
     * 在接收到微信服务器的消息后响应消息
     */
    public void renderMsg(SentMsg outMsg) {
        String outMsgXml = MsgSentBuilder.build(outMsg);

        // 开发模式向控制台输出即将发送的 OutMsg 消息的 xml 内容
        if (JFinal.me().getConstants().getDevMode()) {
            System.out.println("发送消息:");
            System.out.println(outMsgXml);
            System.out.println("--------------------------------------------------------------------------------");
        }

        // 是否需要加密消息
        WcBase base = WcCache.getWxBase("");
        if (base.isEncrypt()) {
            outMsgXml = MsgEncryptUtil.encrypt(base, outMsgXml, getPara("timestamp"), getPara("nonce"));
        }

        renderText(outMsgXml, "text/xml");
    }

    public void renderTextMsg(String content) {
        SentMsg msg = new SentMsg(getReceiveMsg(null));
        msg.setContent(content);
        renderMsg(msg);
    }

    private String getReceivedXmlMsg() {
        String inMsgXml = HttpKit.readIncommingRequestData(getRequest());

        // 是否需要解密消息
        WcBase base = WcCache.getWxBase("");
        if (base.isEncrypt()) {
            inMsgXml = MsgEncryptUtil.decrypt(base, inMsgXml, getPara("timestamp"), getPara("nonce"),
                    getPara("msg_signature"));
        }

        logger.debug(inMsgXml);
        return inMsgXml;
    }

    private ReceivedMsg getReceiveMsg(String xml) {
        if (StringUtils.isBlank(xml)) {
            xml = getReceivedXmlMsg();
        }
        return MsgReceivedParser.parseMsg(xml);
    }

    private void processReceivedMsg(ReceivedMsg msg) {
        switch (MsgType.get(msg.getMsgType())) {
            case TEXT:
                processTextMsg((NormalMsg) msg);
                break;

            case IMAGE:
                processImageMsg((NormalMsg) msg);
                break;

            case VOICE:
                processVoiceMsg((NormalMsg) msg);
                break;

            case VIDEO:
                processVideoMsg((NormalMsg) msg);
                break;

            case SHORT_VIDEO:
                processVideoMsg((NormalMsg) msg);
                break;

            case NEWS:
                processNewsMsg((NormalMsg) msg);
                break;

            case MUSIC:
                processMusicMsg((NormalMsg) msg);
                break;

            case LINK:
                processLinkMsg((NormalMsg) msg);
                break;

            case EVENT:
                processEventMsg((EventMsg) msg);
                break;

            default:
                logger.error("未能识别的消息类型。 消息类型为: " + msg.getMsgType());
                renderNull();
        }
    }

    protected void processMusicMsg(NormalMsg msg) {
        renderNull();
    }

    /**
     * 处理用户发送文字消息
     */
    protected void processTextMsg(NormalMsg msg) {
        renderNull();
    }

    /**
     * 处理用户发送图文消息
     */
    protected void processNewsMsg(NormalMsg msg) {
        renderNull();
    }

    /**
     * 处理视频消息
     */
    protected void processVideoMsg(NormalMsg msg) {
        renderNull();
    }

    /**
     * 处理语音消息
     */
    protected void processVoiceMsg(NormalMsg msg) {
        renderNull();
    }

    /**
     * 处理图片消息
     */
    protected void processImageMsg(NormalMsg msg) {
        renderNull();
    }

    /**
     * 处理链接消息 特别注意：测试时需要发送我的收藏中的曾经收藏过的图文消息，直接发送链接地址会当做文本消息来发送
     */
    protected void processLinkMsg(NormalMsg msg) {
        renderNull();
    }

    private void processEventMsg(EventMsg msg) {
        switch (EventType.get(msg.getEvent())) {
            case SUBSCRIBE:
                processSubcribleEvent(msg);
                break;

            case UNSUBSCRIBE:
                processUnSubcribleEvent(msg);
                break;

            case LOCATION:
                processLocationEvent(msg);
                break;

            case MASSSENDJOBFINISH:
                processMassStatusEvent(msg);
                break;

            case CLICK:
                processClickEvent(msg);
                break;

            case VIEW:
                processViewEvent(msg);
                break;

            case SCAN:
                processScanEvent(msg);
                break;

            case KF_CREATE_SESSION:
                processKfCreateEvent(msg);
                break;

            case KF_CLOSE_SESSION:
                processKfCloseEvent(msg);
                break;

            case KF_SWITCH_SESSION:
                processKfSwitchEvent(msg);
                break;

            case TEMPLATESENDJOBFINISH:
                processTemplateStatusEvent(msg);
                break;

            default:
                renderNull();
        }
    }

    /**
     * 模版消息发送状态
     */
    protected void processTemplateStatusEvent(EventMsg msg) {
        renderNull();
    }

    /**
     * 客服转接会话
     */
    protected void processKfSwitchEvent(EventMsg msg) {
        renderNull();
    }

    /**
     * 客服关闭会话
     */
    protected void processKfCloseEvent(EventMsg msg) {
        renderNull();
    }

    /**
     * 客服接入会话
     */
    protected void processKfCreateEvent(EventMsg msg) {
        renderNull();
    }

    /**
     * 扫描带参数二维码
     */
    protected void processScanEvent(EventMsg msg) {
        renderNull();
    }

    /**
     * 菜单跳转链接
     */
    protected void processViewEvent(EventMsg msg) {
        renderNull();
    }

    /**
     * 菜单拉取消息
     */
    protected void processClickEvent(EventMsg msg) {
        renderNull();
    }

    /**
     * 群发状态
     */
    protected void processMassStatusEvent(EventMsg msg) {
        renderNull();
    }

    /**
     * 上报地理位置
     */
    protected void processLocationEvent(EventMsg msg) {
        renderNull();
    }

    /**
     * 取消关注
     */
    protected void processUnSubcribleEvent(EventMsg msg) {
        renderNull();
    }

    /**
     * 关注
     */
    protected void processSubcribleEvent(EventMsg msg) {
        renderNull();
    }

}
