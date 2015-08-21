package com.codeyn.wechat.sdk.utils;

import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author Arthur
 */
public class SignatureCheckUtil{
	
	public static boolean checkSignature(String token, String signature, String timestamp, String nonce) {
		String array[] = {token, timestamp, nonce};
		Arrays.sort(array);
		String tempStr = new StringBuffer().append(token).append(timestamp).append(nonce).toString();
		tempStr = DigestUtils.sha1Hex(tempStr);
		return tempStr.equalsIgnoreCase(signature);
	}
	
}
