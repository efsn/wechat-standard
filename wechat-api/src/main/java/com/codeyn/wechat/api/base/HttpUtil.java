package com.codeyn.wechat.api.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("rawtypes")
public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private final static String DEFAULT_CHARSET = "UTF-8";// defaultCharsetName

    public static String readUrl(String url) {
        return readUrl(url, ClientFactory.getGlobalConnectTimeOut(), ClientFactory.getGlobalReadTimeout());
    }

    public static String readUrl(String url, int connectTimeout, int readTimeout) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_CHARSET))) {
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

    public static String readUrl(String url, String charsetName) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream(), charsetName))) {
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = br.readLine()) != null)
                sb.append(s);
            return sb.toString();
        } catch (Exception e) {
            logger.error("readUrl error:" + e.getMessage(), e);
            return null;
        }
    }

    public static String postUrl(String url, Map params) {
        return postUrl(url, params, ClientFactory.getGlobalConnectTimeOut(), ClientFactory.getGlobalReadTimeout());
    }

    public static String postUrl(String url, Map params, int connectTimeout, int readTimeout) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setDoInput(true);
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + DEFAULT_CHARSET);
            conn.setRequestMethod("POST");
            if (params != null && !params.isEmpty()) {
                conn.setDoOutput(true);
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(),
                        DEFAULT_CHARSET))) {
                    String buildParams = buildParams(params);
                    writer.write(buildParams);
                }
            }
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_CHARSET))) {
                String line;
                while ((line = reader.readLine()) != null)
                    sb.append(line);
            }
            conn.disconnect();
            return sb.toString();
        } catch (Exception e) {
            logger.error("readUrl error:" + url, e);
            return null;
        }
    }

    public static String postUrl(String url, Map params, String charsetName) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + DEFAULT_CHARSET);
            conn.setRequestMethod("POST");
            if (params != null && !params.isEmpty()) {
                conn.setDoOutput(true);
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(),
                        DEFAULT_CHARSET))) {
                    writer.write(buildParams(params));
                }
            }
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), charsetName))) {
                String line;
                while ((line = reader.readLine()) != null)
                    sb.append(line);
            }
            conn.disconnect();
            return sb.toString();
        } catch (Exception e) {
            logger.error("readUrl error:" + url, e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private static String buildParams(Map params) throws UnsupportedEncodingException {
        boolean f = false;
        StringBuilder sb = new StringBuilder();
        for (Map.Entry kv : (Set<Map.Entry>) params.entrySet()) {
            if (f)
                sb.append("&");
            Object v = kv.getValue();
            if (v instanceof List) {
                List l = (List) v;
                for (Object item : l) {
                    sb.append("&");
                    sb.append(kv.getKey()).append("=").append(URLEncoder.encode((String) item, DEFAULT_CHARSET));
                }
            } else {
                sb.append(kv.getKey()).append("=")
                        .append(kv.getValue() == null ? "" : URLEncoder.encode(kv.getValue().toString(), DEFAULT_CHARSET));
            }
            f = true;
        }
        return sb.toString();
    }

    public static String readFile(String path) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)),
                DEFAULT_CHARSET))) {
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = br.readLine()) != null)
                sb.append(s);
            return sb.toString();
        } catch (Exception e) {
            logger.error("readUrl error:" + e.getMessage(), e);
            return null;
        }
    }

    public static String readFile(String path, String charsetName) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)),
                charsetName))) {
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = br.readLine()) != null)
                sb.append(s);
            return sb.toString();
        } catch (Exception e) {
            logger.error("readUrl error:" + e.getMessage(), e);
            return null;
        }
    }

    public static String readFile(File f) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), DEFAULT_CHARSET))) {
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = br.readLine()) != null)
                sb.append(s);
            return sb.toString();
        } catch (Exception e) {
            logger.error("readUrl error:" + e.getMessage(), e);
            return null;
        }
    }

    public static String readFile(File f, String charsetName) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), charsetName))) {
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = br.readLine()) != null)
                sb.append(s);
            return sb.toString();
        } catch (Exception e) {
            logger.error("readUrl error:" + e.getMessage(), e);
            return null;
        }
    }

    public static boolean writeFile(String path, String content, boolean append) {
        return writeFile(new File(path), content, append);
    }

    public static boolean writeFile(File f, String content, boolean append) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f, append))) {
            bw.write(content);
            return true;
        } catch (Exception e) {
            logger.error("readUrl error:" + e.getMessage(), e);
            return false;
        }
    }
}
