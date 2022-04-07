package com.rolex.mall.config;



import lombok.Data;

import java.io.FileWriter;
import java.io.IOException;
//bdamie5521@sandbox.com
@Data
public class AlipayConfig {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016101100658402";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCb4WQHZoBTu/yip7qx84DIW1e9+PZjwk71r/J+QyfxoZZA7y93s6EFBB0UiatA133shcPqN0jd9jz8iGSCDjIRbBhRfkQq9O/iearvzeS8m5f66G0dN9jRS9lEGhpGrt7K8PZK03kJ8892KxGlBIN/Zc8rNKRfG+O5aVCiLuf4MKlUoWDumt6K2UFwmoSp3w01YG9uWFnySRNiUgUCFflUK4ikJNGAfghKF0OtQvqGvQRVEebqDrluajsHo0vInT6TV1+ThMLq9EtfbHjcOY/UG4w94a+ThY9jGcZnrDvRM0EV2Rg6cBCzGLHPo1KyvPsnOFlg+Td5YodWDdD5T5zVAgMBAAECggEAaKjCIn3dt1rGWudG4GfflaZOmQxpUb2KikbiB4hLb17QpTRyqGoAuH90ju+H/fcxYvbE/aK01+KWO+/Pm8qwLyZmhYDDgBwIXATYkj2pUEEfn6UibbWra4sDi0gDUGXkzFlQ5BhschQyvOJZ7HPFXmoa2KV1ny34jep56wqXUPk+SEnJ9PpM2OOPRBf2wmRoT32gtglQUaEcKAlwqWD7HlUb8izHzq9ExsvnOr+MqylN4D1OvbDw94YoyBxc8EBYCZg8O66P3Tp9G8IvjWYuewrHgJp2FSeZBXrOCiXD59iR6Fl2yog3EGsl4dzU/vKrxayvZFody+SEx1PiEe/jgQKBgQDVHLbAlCOWYl/PjCn6UyZeMMxw2pXVIviDSMHx4HqbfsG0XPEiqGywnETHywzhY+dDdATrFu+uMfZM3NRNqbiU/XSxx3R0etjXdAkfbei8SbS5VJz1dNgmZkAp5Oaq/NoTPxjwySlIIizo796KiTQZ26CVQyBX85I4j1ODweAfJQKBgQC7QCp4hPtWhX0n4/SUMTZbtLXIoqpKeVFMFOwlIIzUmsdzE5e9WGqXrPr/71f5NwACPYCOhgMvEmH8cVySRrQdtiNu7d9wQ7bn1ZA4m9A6UBZP9xGbWGSaiderxYEfmnsHdWK2MODATQxIJBcTv/nw93yCPXbu9qIHMYp1ZWiv8QKBgGIaYsKKL0u2I3buhhB3II1xl1tINHs2KwJ8htNqy+Yy1sbJljOnJsmyVjNG9Ln4/Gffy7fRCiq9Uy2U5qKNw6vKdWK1d5V7S8D66IwwU1gB8hDys4KG9E3Q+i6O8eS6m0UK4ddOMxPCr7vXWDGb8YEJyoWLriLuvFwPFpnrsNmFAoGAeLRGLGZctktagEpbsOOATOFShUEGUPefRlH+X3h6D9quUyoAGJhULe3d5vlcVlPz9325zyXoC4HoFBjnOYvGM5jjgnBoJjAgSZXLYBJgyhYyK93yRZdXUp8knZ+1OrAUOpyawyMC3jNMhXxuO9b2wTRn2HTSo/vsOoLe5Ao4z6ECgYBsu40BU2RUjGCfRm7VIUloknZSYdBI1ATfY3RZHjaj2YKmPb/MB9rHkruFKK+bdHHSo//dERdTvgbUqcy60AWMpYdfB9KE9Csuz4vY2YOLSZlgvD/WOavJeLuZ6PmZ/OEO54LQ8RDL0oJhOXL5anExko9uQdcN0YSzqGJsYhguDw==";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm+FkB2aAU7v8oqe6sfOAyFtXvfj2Y8JO9a/yfkMn8aGWQO8vd7OhBQQdFImrQNd97IXD6jdI3fY8/Ihkgg4yEWwYUX5EKvTv4nmq783kvJuX+uhtHTfY0UvZRBoaRq7eyvD2StN5CfPPdisRpQSDf2XPKzSkXxvjuWlQoi7n+DCpVKFg7preitlBcJqEqd8NNWBvblhZ8kkTYlIFAhX5VCuIpCTRgH4IShdDrUL6hr0EVRHm6g65bmo7B6NLyJ0+k1dfk4TC6vRLX2x43DmP1BuMPeGvk4WPYxnGZ6w70TNBFdkYOnAQsxixz6NSsrz7JzhZYPk3eWKHVg3Q+U+c1QIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8080/home";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:8080/home";

    // 签名方式
    public static String sign_type = "RSA";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "D:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
