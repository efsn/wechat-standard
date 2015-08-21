package com.codeyn.wechat.sdk.base;

import java.lang.reflect.Constructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeyn.wechat.sdk.base.model.WxBase;

/**
 * @author Arthur
 */
public class WxClientFactory {

	private static Logger logger = LoggerFactory.getLogger(WxClientFactory.class);

	private static int GLOBAL_CONNECT_TIMEOUT = 1000;

	private static int GLOBAL_READ_TIMEOUT = 5000;

	public static <T extends WxClient> T getClient(Class<T> clazz, WxBase wxBase) {
		try {
			Constructor<T> constructor = clazz.getConstructor(WxBase.class);
			return constructor.newInstance(wxBase);
		} catch (Exception e) {
			logger.error("Class name: " + clazz.getName(), e);
		}
		return null;
	}

	/**
	 * 设置全局连接超时时间
	 */
	public static void setGlobalConnectTimeOut(int connectTimeOut) {
		GLOBAL_CONNECT_TIMEOUT = connectTimeOut;
	}

	/**
	 * 设置全局等待响应超时时间
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
