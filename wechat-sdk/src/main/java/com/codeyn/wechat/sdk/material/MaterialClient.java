package com.codeyn.wechat.sdk.material;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.codeyn.wechat.sdk.base.WcClient;
import com.codeyn.wechat.sdk.base.model.WcBase;
import com.codeyn.wechat.sdk.base.model.WcResult;
import com.codeyn.wechat.sdk.material.enums.MediaType;
import com.codeyn.wechat.sdk.material.result.MaterialCount;
import com.codeyn.wechat.sdk.material.result.Media;
import com.codeyn.wechat.sdk.material.result.MediaList;
import com.codeyn.wechat.sdk.material.result.NewsList;
import com.codeyn.wechat.sdk.material.result.Video;
import com.codeyn.wechat.sdk.msg.result.Article;

/**
 * 素材管理
 * 
 * 上传的临时多媒体文件有格式和大小限制，如下： 图片（image）: 1M，支持JPG格式
 * 语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式 视频（video）：10MB，支持MP4格式
 * 缩略图（thumb）：64KB，支持JPG格式 媒体文件在后台保存时间为3天，即3天后media_id失效。
 * 
 * 
 * 
 * @author Arthur
 *
 */
public class MaterialClient extends WcClient {

    public MaterialClient(WcBase WcBase) {
        super(WcBase);
    }

    /**
     * 上传图文消息内的图片获取URL 图片仅支持jpg/png格式，大小必须在1MB以下
     */
    public Media uploadNewsImg(String accessToken, File file) {
        StringBuffer urlStr = new StringBuffer(getWcBase().getHost()).append("/cgi-bin/media/uploadimg?access_token=")
                .append(accessToken);
        return uploadMedia(urlStr.toString(), file);
    }

    /**
     * 新增图文素材
     */
    public Media uploadNews(String accessToken, final String json, boolean isTemp) {
        StringBuffer sb = new StringBuffer("/cgi-bin/").append(isTemp ? "media/uploadnews" : "material/add_news")
                .append("?access_token=").append(accessToken);
        return doPost(Media.class, new ParamService() {

            @Override
            public void init(Map<String, String> map) {
                map.put(KEY, json);
            }

        }, sb.toString());
    }

    /**
     * 上传素材
     * 
     * @param type
     *            只支持 image，voice，video，thumb
     */
    public Media uploadMedia(MediaType type, File file, String accessToken, boolean isTemp) {
        StringBuffer urlStr = new StringBuffer(getWcBase().getHost()).append("/cgi-bin/")
                .append(isTemp ? "media/upload" : "material/add_material").append("?access_token=").append(accessToken)
                .append("&type=").append(type.getFlag());
        return uploadMedia(urlStr.toString(), file);
    }

    /**
     * @param media_id
     *            需通过基础支持中的上传下载多媒体文件来得到
     */
    public Media uploadVideo(String accessToken, final String mediaId, final String title, final String description) {
        return doPost(Media.class, new ParamService() {

            @Override
            public void init(Map<String, String> map) {
                JSONObject json = new JSONObject();
                json.put("media_id", mediaId);
                json.put("title", title);
                json.put("description", description);
                map.put(KEY, json.toJSONString());
            }

        }, "/cgi-bin/media/uploadvideo?access_token=" + accessToken);
    }

    /**
     * 下载多媒体文件（临时素材）
     * 
     * video is http
     * 
     * @param type
     *            image/voice/video/thumb
     * @param media
     *            form-data中媒体文件标识，有filename/filelength/content-type等信息
     */
    public Media getMedia(String accessToken, final String type, final String media) {
        return doPost(Media.class, new ParamService() {

            @Override
            public void init(Map<String, String> map) {
                // TODO
            }

        }, "/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID");
    }

    /**
     * 新增其他类型永久素材
     * 通过POST表单来调用接口，表单id为media，包含需要上传的素材内容，有filename/filelength/content-type等信息
     * 
     * TODO 在上传视频素材时需要POST另一个表单，id为description，包含素材的描述信息，内容格式为JSON
     */
    public Media addOtherMaterial(String accessToken, File file, MediaType type) {
        StringBuffer urlStr = new StringBuffer(getWcBase().getHost())
                .append("/cgi-bin/material/add_material?access_token=").append(accessToken).append("&type=")
                .append(type.getFlag());
        return uploadMedia(urlStr.toString(), file);
    }

    /**
     * 获取永久图文素材 临时素材无法通过本接口获取
     * 
     */
    public Article getNews(String accessToken, final String mediaId) {
        return doPost(Article.class, new ParamService() {

            @Override
            public void init(Map<String, String> map) {
                JSONObject json = new JSONObject();
                json.put("media_id", mediaId);
                map.put(KEY, json.toJSONString());
            }

        }, "/cgi-bin/material/get_material?access_token=" + accessToken);
    }

    /**
     * 获取永久视频素材 临时素材无法通过本接口获取
     * 
     */
    public Video getVideo(String accessToken, final String mediaId) {
        return doPost(Video.class, new ParamService() {

            @Override
            public void init(Map<String, String> map) {
                JSONObject json = new JSONObject();
                json.put("media_id", mediaId);
                map.put(KEY, json.toJSONString());
            }

        }, "/cgi-bin/material/get_material?access_token=" + accessToken);
    }

    /**
     * 其他类型的素材消息，则响应的直接为素材的内容，开发者可以自行保存为文件
     * 
     * @param mediaId
     * @return
     */
    public File getOthers(final String mediaId) {
        // TODO
        return null;
    }

    /**
     * 删除永久素材
     */
    public WcResult deleteMaterial(String accessToken, final String mediaId) {
        return doPost(WcResult.class, new ParamService() {

            @Override
            public void init(Map<String, String> map) {
                JSONObject json = new JSONObject();
                json.put("media_id", mediaId);
                map.put(KEY, json.toJSONString());
            }

        }, "/cgi-bin/material/del_material?access_token=" + accessToken);
    }

    /**
     * 修改永久图文素材
     */
    public WcResult updateNews(String accessToken, final String json) {
        return doPost(WcResult.class, new ParamService() {

            @Override
            public void init(Map<String, String> map) {
                map.put(KEY, json);
            }

        }, "/cgi-bin/material/update_news?access_token=" + accessToken);
    }

    /**
     * 获取素材总数
     */
    public MaterialCount getMaterialCount(final String accesstoken) {
        return doGet(MaterialCount.class, new ParamService() {

            @Override
            public void init(Map<String, String> map) {
                map.put("access_token", accesstoken);
            }

        }, "/cgi-bin/material/get_materialcount");
    }

    /**
     * 分类型获取永久图文素材列表
     */
    public NewsList getNewsList(String accessToken, final Integer offset, final Integer count) {
        return doPost(NewsList.class, new ParamService() {

            @Override
            public void init(Map<String, String> map) {
                JSONObject json = new JSONObject();
                json.put("type", "news");
                json.put("offset", offset);
                json.put("count", count);
                map.put(KEY, json.toJSONString());
            }

        }, "/cgi-bin/material/batchget_material?access_token=" + accessToken);
    }

    /**
     * 分类型获取其他类型永久素材的列表
     */
    public MediaList getMediaList(String accessToken, final String type, final Integer offset, final Integer count) {
        return doPost(MediaList.class, new ParamService() {

            @Override
            public void init(Map<String, String> map) {
                JSONObject json = new JSONObject();
                json.put("type", type);
                json.put("offset", offset);
                json.put("count", count);
                map.put(KEY, json.toJSONString());
            }

        }, "/cgi-bin/material/batchget_material?access_token=" + accessToken);
    }

    /**
     * 上传素材到腾讯服务器
     * 
     * @param type
     *            目前腾讯type 只支持 image，voice，video，thumb
     */
    private Media uploadMedia(String urlStr, File file) {
        String res = "";
        HttpURLConnection conn = null;
        String boundary = "-----------------------------123821742118716";
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(50000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            StringBuffer strBuf = new StringBuffer();
            strBuf.append("\r\n--").append(boundary).append("\r\n");
            strBuf.append("Content-Disposition: form-data; name=\"media\"; filename=\"" + file.getName() + "\"\r\n");
            strBuf.append("Content-Type:application/octet-stream\r\n\r\n");
            out.write(strBuf.toString().getBytes());
            int len = 0;
            byte[] bufferOut = new byte[1024];
            FileInputStream in = new FileInputStream(file);
            while ((len = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, len);
            }
            in.close();
            byte[] endData = ("\r\n--" + boundary + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();

            // 读取返回数据
            StringBuffer rs = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                rs.append(line).append("\r\n");
            }
            res = rs.toString();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return JSON.parseObject(res, Media.class);
    }

}
