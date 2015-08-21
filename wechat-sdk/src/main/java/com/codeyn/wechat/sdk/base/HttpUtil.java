package com.codeyn.wechat.sdk.base;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeyn.wechat.sdk.base.model.WxBase;

/**
 * 
 * Http request/response
 * 
 * @author Arthur
 */
public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static final String GET = "GET";
    private static final String POST = "POST";
    
    private static final String DIR = System.getProperty("java.io.tmpdir");

    private static final SSLSocketFactory sslSocketFactory = initSSLSocketFactory();
    private static final TrustAnyHostnameVerifier trustAnyHostnameVerifier = new HttpUtil().new TrustAnyHostnameVerifier();

    private HttpUtil() {
    }

    public static String readUrl(String url) {
        return readUrl(url, WxClientFactory.getGlobalConnectTimeOut(), WxClientFactory.getGlobalReadTimeout());
    }

    public static String readUrl(String url, int connectTimeout, int readTimeout) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                    WxBase.ENCODING))) {
                String line;
                while ((line = reader.readLine()) != null)
                    sb.append(line);
            }
            conn.disconnect();
            return sb.toString();
        } catch (Exception e) {
            logger.error("readUrl error:" + e.getMessage(), e);
            return null;
        }
    }

    /**
     * https 域名校验
     */
    private class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * https 证书管理
     */
    private class TrustAnyTrustManager implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }

    private static SSLSocketFactory initSSLSocketFactory() {
        try {
            TrustManager[] tm = { new HttpUtil().new TrustAnyTrustManager() };
            SSLContext sslContext = SSLContext.getInstance("TLS", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static HttpURLConnection getHttpConnection(int connectTimeout, int readTimeout, String urlStr,
            String method, Map<String, String> headers) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if (conn instanceof HttpsURLConnection) {
            ((HttpsURLConnection) conn).setSSLSocketFactory(sslSocketFactory);
            ((HttpsURLConnection) conn).setHostnameVerifier(trustAnyHostnameVerifier);
        }

        conn.setRequestMethod(method);
        conn.setDoOutput(true);
        conn.setDoInput(true);

        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);

        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");

        if (headers != null && !headers.isEmpty())
            for (Entry<String, String> entry : headers.entrySet())
                conn.setRequestProperty(entry.getKey(), entry.getValue());

        return conn;
    }

    /**
     * Send GET request
     */
    public static String get(int connectTimeout, int readTimeout, String url, Map<String, String> queryParas,
            Map<String, String> headers) {
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(connectTimeout, readTimeout, buildUrlWithQueryString(url, queryParas), GET,
                    headers);
            conn.connect();
            return readResponseString(conn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static String get(int connectTimeout, int readTimeout, String url, Map<String, String> queryParas) {
        return get(connectTimeout, readTimeout, url, queryParas, null);
    }

    public static String get(int connectTimeout, int readTimeout, String url) {
        return get(connectTimeout, readTimeout, url, null, null);
    }

    /**
     * Send POST request
     */
    public static String post(int connectTimeout, int readTimeout, String url, Map<String, String> queryParas,
            String data, Map<String, String> headers) {
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(connectTimeout, readTimeout, buildUrlWithQueryString(url, queryParas), POST,
                    headers);
            conn.connect();

            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes(WxBase.ENCODING));
            out.flush();
            out.close();

            return readResponseString(conn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static String post(int connectTimeout, int readTimeout, String url, Map<String, String> queryParas,
            String data) {
        return post(connectTimeout, readTimeout, url, queryParas, data, null);
    }

    public static String post(int connectTimeout,
                              int readTimeout,
                              String url,
                              String data,
                              Map<String, String> headers) {
        return post(connectTimeout, readTimeout, url, null, data, headers);
    }

    public static String post(int connectTimeout, int readTimeout, String url, String data) {
        return post(connectTimeout, readTimeout, url, null, data, null);
    }

    private static String readResponseString(HttpURLConnection conn) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, WxBase.ENCODING));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private static File readResponseFile(HttpURLConnection conn, String fileName) {
        StringBuffer name = new StringBuffer(DIR).append(File.separator).append(fileName);
        File file = new File(name.toString());
        
        InputStream in = null;
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            in = new BufferedInputStream(conn.getInputStream());
            byte[] buf = new byte[2048];
            int len = -1;
            while ((len = in.read(buf)) > -1) {
                out.write(buf, 0, len);
            }
            out.flush();
            return file;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Build queryString of the url
     */
    private static String buildUrlWithQueryString(String url, Map<String, String> queryParas) {
        if (queryParas == null || queryParas.isEmpty())
            return url;

        StringBuilder sb = new StringBuilder(url);
        boolean isFirst;
        if (url.indexOf("?") == -1) {
            isFirst = true;
            sb.append("?");
        } else {
            isFirst = false;
        }

        for (Entry<String, String> entry : queryParas.entrySet()) {
            if (isFirst)
                isFirst = false;
            else
                sb.append("&");

            String key = entry.getKey();
            String value = entry.getValue();
            if (StringUtils.isNotBlank(value))
                try {
                    value = URLEncoder.encode(value, WxBase.ENCODING);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            sb.append(key).append("=").append(value);
        }
        return sb.toString();
    }

    public static String readIncommingRequestData(HttpServletRequest request) {
        BufferedReader br = null;
        try {
            StringBuilder result = new StringBuilder();
            br = request.getReader();
            for (String line = null; (line = br.readLine()) != null;) {
                result.append(line).append("\n");
            }
            return result.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static File readFile(int connectTimeout, int readTimeout, String url){
        return readFile(connectTimeout, readTimeout, url, null, null);
    }
    
    public static File readFile(int connectTimeout, int readTimeout, String url, Map<String, String> queryParas){
        return readFile(connectTimeout, readTimeout, url, queryParas, null);
    }
    
    public static File readFile(int connectTimeout, int readTimeout, String url, Map<String, String> queryParas, Map<String, String> headers){
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(connectTimeout, readTimeout, buildUrlWithQueryString(url, queryParas), GET,
                    headers);
            conn.connect();
            String cd = conn.getHeaderField("Content-disposition");
            System.out.println(cd);
            
            return readResponseFile(conn, "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
    
    public static void main(String[] args) {
        readFile(1000,5000,"http://midplat-img.b0.upaiyun.com/material/2015/06/11/weixin/testl0YVNs3bq6!wjl");
    }

    public static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getRealIpV2(HttpServletRequest request) {
        String accessIP = request.getHeader("x-forwarded-for");
        if (null == accessIP)
            return request.getRemoteAddr();
        return accessIP;
    }

}
