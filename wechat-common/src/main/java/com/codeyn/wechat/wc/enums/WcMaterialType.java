package com.codeyn.wechat.wc.enums;

/**
 * 微信素材类型
 * @author Arthur
 *
 */
public enum WcMaterialType {
	
	TEXT("text", "文本信息"),
	
	NEWS("news", "高级图文"),
	
	MPNEWS("mpnews", "微信图文"),
	
	IMAGE("image", "图片素材"),
	
	VOICE("voice", "语音素材"),
	
	THUMB("thumb", "缩略图素材"),
	
	VIDEO("video", "视频素材");

	private String flag;
    private String name;
    
    private WcMaterialType(String flag, String name){
        this.flag = flag;
        this.name = name;
    }
    
    public static WcMaterialType get(String flag){
        for(WcMaterialType type : values()){
            if(type.getFlag().equals(flag)){
                return type;
            }
        }
        return null;
    }

    public String getFlag() {
        return flag;
    }

    public String getName() {
        return name;
    }
}
