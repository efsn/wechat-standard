package com.codeyn.wechat.service.material;

import java.io.File;
import java.util.Date;

import com.codeyn.base.common.Assert;
import com.codeyn.wechat.sdk.base.WcClientFactory;
import com.codeyn.wechat.sdk.base.model.WcResult;
import com.codeyn.wechat.sdk.material.MaterialClient;
import com.codeyn.wechat.sdk.material.enums.MediaType;
import com.codeyn.wechat.sdk.material.result.Media;
import com.codeyn.wechat.utils.FileUtil;
import com.codeyn.wechat.wc.model.material.WcMedia;
import com.codeyn.wechat.wc.utils.WcCache;

/**
 * 微信素材（暂不支持视频）
 * @author Arthur
 *
 */
public class WxMediaService {
    
    private static MaterialClient getClient(String tenantId) {
        return WcClientFactory.getClient(MaterialClient.class, WcCache.getWxBase(tenantId));
    }
	
    /**
     * 从本地库获取
     */
	public static WcMedia getMedia(String tenantId, String mediaId){
	    return WcMedia.me.getMedia(tenantId, mediaId);
	}
	
	public static WcMedia getMedia(Integer id){
	    return WcMedia.me.findById(id);
	}
	
	/**
	 * @param url 图片上传组件返回的nginx地址
	 */
	public static String upload(String tenantId, MediaType type, String url, boolean isForever){
	    File file = FileUtil.download(url);
	    return upload(tenantId, type, file, isForever);
	}
	
	/**
	 * 上传文件到upyun和微信服务器
	 * 
	 */
	public static String upload(String tenantId, MediaType type, File file, boolean isForever){
	    Media media = null;
	    
	    //上传到upyun TODO
	    String url = "";
	    
	    // 上传到微信服务器
	    String accessToken = WcCache.getAccessToken(tenantId);
	    if(isForever){
	           media = getClient(tenantId).uploadAbidingMedia(type, file, accessToken);
	    }else{
	        media = getClient(tenantId).uploadTempMedia(type, file, accessToken);
	    }
	    
	    // 删除临时文件
	    file.delete();
        
        // 回调校验accessToken是否有效
        WcCache.refreshAccessTokenIfInvalid(media, tenantId);
        Assert.isTrue(media != null && media.isSuccess(), "media upload to wechat fail");
        
        // 将信息存库
        WcMedia wm = new WcMedia();
        wm.set("tenant_id", tenantId);
        wm.set("media_id", media.getMedia_id());
        wm.set("type", type.getFlag());
        wm.set("url", url);
        if(!isForever){
            // 临时素材 3 * 24 * 60 * 60 * 1000
            wm.set("expires_time", new Date(media.getCreated_at() + 3 * 24 * 60 * 60 * 1000L - 5L));
        }
            
        wm.set("create_time", new Date(media.getCreated_at()));
        Assert.isTrue(wm.save(), "save wxmedia");
        
        return media.getMedia_id();
	}
	
	/**
	 * 网络提取图片到本地
	 */
	public static File fetchImg(String url){
	    return FileUtil.download(url);
	}
	
	/**
	 * 删除永久素材
	 */
	public static WcResult deleteAbidingMedia(String tenantId, String mediaId){
	    WcResult rs = getClient(tenantId).deleteMaterial(WcCache.getAccessToken(tenantId), mediaId);
	    if(rs != null && rs.isSuccess()){
	        WcMedia.me.delete(tenantId, mediaId);
	    }
	    return rs;
	}
	
}
