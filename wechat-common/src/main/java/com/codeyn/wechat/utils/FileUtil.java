package com.codeyn.wechat.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class FileUtil {

    public static File download(String urlString) {
        StringBuffer sb = new StringBuffer(System.getProperty("java.io.tmpdir")).append(File.separator)
                .append(System.currentTimeMillis()).append(".jpg");
        File outFile = new File(sb.toString());
        URL url = null;
        OutputStream os = null;
        InputStream is = null;
        try {
            url = new URL(urlString);
            os = new FileOutputStream(outFile);
            is = url.openStream();
            byte[] buff = new byte[1024];
            while (true) {
                int count = is.read(buff);
                if (count == -1) {
                    break;
                }
                os.write(buff, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return outFile;
    }
}
