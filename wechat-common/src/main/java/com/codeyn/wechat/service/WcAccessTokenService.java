package com.codeyn.wechat.service;

import com.codeyn.wechat.wc.model.WcAccessToken;

/**
 * @author Arthur
 *
 */
public class WcAccessTokenService {
	
    public static boolean save(WcAccessToken token){
        return token.getInt("id") != null ? token.update() : token.save();
    }
	
}
