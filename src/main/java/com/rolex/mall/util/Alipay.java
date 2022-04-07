package com.rolex.mall.util;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.rolex.mall.config.AlipayConfig;
import com.rolex.mall.pojo.pay.AlipayBean;
import org.springframework.stereotype.Component;

@Component
public class Alipay {
    /**
     * 支付接口
     * @param alipayBean
     * @return
     * @throws AlipayApiException
     */
    public String pay(AlipayBean alipayBean) throws AlipayApiException {
        // 1、获得初始化的AlipayClient
        String serverUrl = AlipayConfig.gatewayUrl;
        String appId = AlipayConfig.app_id;
        String privateKey = AlipayConfig.merchant_private_key;
        String format = "json";
        String charset = AlipayConfig.charset;
        String alipayPublicKey = AlipayConfig.alipay_public_key;
        String signType = AlipayConfig.sign_type;
        String returnUrl = AlipayConfig.return_url;
        String notifyUrl = AlipayConfig.notify_url;
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, format, charset, alipayPublicKey, signType);
        // 2、设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        // 页面跳转同步通知页面路径
        alipayRequest.setReturnUrl(returnUrl);
        // 服务器异步通知页面路径
        alipayRequest.setNotifyUrl(notifyUrl);
        // 封装参数
        alipayRequest.setBizContent(JSON.toJSONString(alipayBean));
        // 3、请求支付宝进行付款，并获取支付结果
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        // 返回付款信息
        return result;
    }
}

