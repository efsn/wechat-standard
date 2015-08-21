package com.codeyn.wechat.sdk.msg.utils;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.codeyn.wechat.sdk.msg.model.receive.NormalMsg;
import com.codeyn.wechat.sdk.msg.model.receive.ReceivedMsg;

/**
 * Xml to Json
 * Json to Xml
 * @author Arthur
 *
 */
public class SwitchUtil {

    @SuppressWarnings("unchecked")
    public static JSONObject xml2Json(Element root){
        JSONObject json = new JSONObject();
        if(root != null){
            List<Element> list = root.elements();
            if(list != null && !list.isEmpty())
                for(Element e : list) {
                    if(e.isTextOnly()){
                        json.put(e.getName(), e.getTextTrim());
                    }else{
                        json.put(e.getName(), xml2Json(e));
                    }
                }
        }
        return json;
    }
    
    public static <T> T xml2Json(Element root, Class<T> clazz){
        return JSON.parseObject(xml2Json(root).toJSONString(), clazz);
    }
    
    public static void main(String[] args) throws Exception {
        
        String xml = "<xml> "
                + "<ToUserName><![CDATA[toUser]]></ToUserName> "
                + "<FromUserName><![CDATA[fromUser]]></FromUserName> "
                + "<CreateTime>1348831860</CreateTime>"
                + "<MsgType><![CDATA[text]]></MsgType>"
                + "<Content><![CDATA[this is a test]]></Content>"
                + "<MsgId>1234567890123456</MsgId>"
                + "<Image>"
                + "<MediaId><![CDATA[media_id]]></MediaId>"
                + "</Image>"
                + "</xml>";
        
        
        Document doc = DocumentHelper.parseText(xml);
        
        
        JSONObject json = xml2Json(doc.getRootElement());
        
//        Msg txt = JSON.parseObject(json.toJSONString(), Msg.class);
        
        ReceivedMsg txt = xml2Json(doc.getRootElement(), NormalMsg.class);
        
        System.out.println(JSON.toJSONString(txt));
        
        System.out.println();
        
        System.out.println(json.toJSONString());
        
        System.out.println("-------------------------------------------------------------------------------");
        
        Document dd = DocumentHelper.createDocument();
        Element root = dd.addElement("xml");
        
        Class<?> clazz = txt.getClass();
        
        for(Field f : clazz.getDeclaredFields()){
            
            f.setAccessible(true);
            
            String name = f.getName();
            
            if(StringUtils.isNotBlank(name)){
                StringBuffer sb = new StringBuffer();
                sb.append(name.substring(0, 1).toUpperCase()).append(name.substring(1));
                name = sb.toString();
            }
            
            Object x = f.get(txt);
            
            if(x != null){
                Element e = root.addElement(name);
                if(String.class.equals(f.getType())){
                    e.addCDATA(x.toString());
                }else{
                    e.addText(f.get(txt).toString());
                }
            }
        }
        
        System.out.println(dd.asXML().substring(dd.asXML().indexOf("?>") + 2));
        
        
    }
    
    
    
    
}
