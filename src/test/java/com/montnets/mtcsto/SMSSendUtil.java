package com.montnets.mtcsto;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.rolex.mall.service.AliYunMessageService;
import com.rolex.mall.service.impl.AliYunMessageServiceImpl;
import com.rolex.mall.util.CheckSumBuilder;

public class SMSSendUtil {
    AliYunMessageService aliYunMessageService = new AliYunMessageServiceImpl();

    public void sendMessageToIphone() throws ClientException, InterruptedException {
        //1.准备好请求参数：phoneNumber、TemplateParam
        String phoneNumber = "17673130854";
        //随机生成手机验证码
        String code = CheckSumBuilder.getCheckSum();
        //2.调用接口，发短信
        SendSmsResponse response = aliYunMessageService.sendSms(phoneNumber, code);
        Thread.sleep(3000L);
        //查询发送消息接口记录
        if (response.getCode() != null && response.getCode().equals("OK")) {
            QuerySendDetailsResponse querySendDetailsResponse = aliYunMessageService.querySendDetails(response.getBizId(), phoneNumber);
            //对返回结果true 或者false进行一个全局判断
            String responseCode = querySendDetailsResponse.getCode();
            String responseMessage = querySendDetailsResponse.getMessage();
            //OK代表信息发送成功
            if ("OK".equals(responseCode) && "OK".equals(responseMessage)) {
                System.out.println(responseCode);
                System.out.println(responseMessage);
            }
        }
    }

    public static void main(String[] args) throws ClientException, InterruptedException {
        new SMSSendUtil().sendMessageToIphone();
    }

}
