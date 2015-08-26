package com.codeyn.wechat.sdk.base;

import java.lang.reflect.Constructor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author parcel
 * 
 */
public class ClientFactory {

    private static Log logger = LogFactory.getLog(ClientFactory.class);

    private static int GLOBAL_CONNECT_TIMEOUT = 1000;

    private static int GLOBAL_READ_TIMEOUT = 5000;

    public static <T extends WcApiClient> T getClient(Class<T> clazz, SysBase appBase) {
        try {
            Constructor<T> constructor = clazz.getConstructor(SysBase.class);
            return constructor.newInstance(appBase);
        } catch (Exception e) {
            logger.error("class name:" + clazz.getName(), e);
        }
        return null;
    }

    /**
     * 设置全局连接超时时间
     * 
     * @param connectTimeOut
     */
    public static void setGlobalConnectTimeOut(int connectTimeOut) {
        GLOBAL_CONNECT_TIMEOUT = connectTimeOut;
    }

    /**
     * 设置全局等待响应超时时间
     * 
     * @param connectTimeOut
     */
    public static void setGlobalReadTimeout(int readTimeout) {
        GLOBAL_READ_TIMEOUT = readTimeout;
    }

    public static int getGlobalConnectTimeOut() {
        return GLOBAL_CONNECT_TIMEOUT;
    }

    public static int getGlobalReadTimeout() {
        return GLOBAL_READ_TIMEOUT;
    }
}
