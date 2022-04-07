package com.rolex.mall.util;


/**
 * @Author tanghh
 * @Date 2019/12/30 15:45
 */
public class AliYunParameterUtil {
    /**
     * 这个需要替换自己的AK(在阿里云的Accesskey管理中寻找)
     */
    public static String accessKeyId = "LTAI4Fzxej8ZnXuFizx783ts";
    public static String  accessSecret = "f4DCu2ZrtvGZYjQOORa6WTrcGixhrz";

    /**
     * 签名名称（需要替换以及只有审核后才能使用）在阿里云控制台中找到签名管理中的签名名称
     */
    public static String signName ="极客猫商城";

    /**
     * 模板code (需要替换 以及只有审核后才能使用) 在阿里云控制台中找到模板管理中的模板code
     */
    public static String TemplateCode ="SMS_188632322";

    /**
     * 产品名称:云通信短信API产品,开发者无需替换
     */
    public static final String PRODUCT = "Dysmsapi";
    /**
     * 产品域名,开发者无需替换
     */
    public static final String DOMAIN = "dysmsapi.aliyuncs.com";



    public static String specialUrlEncode(String value) throws Exception {
        return java.net.URLEncoder.encode(value, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
    }
    public static String sign(String accessSecret, String stringToSign) throws Exception {
        javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA1");
        mac.init(new javax.crypto.spec.SecretKeySpec(accessSecret.getBytes("UTF-8"), "HmacSHA1"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        return new sun.misc.BASE64Encoder().encode(signData);
    }
}