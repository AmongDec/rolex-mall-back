package com.rolex.mall.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.rolex.mall.service.AliYunMessageService;
import com.rolex.mall.util.AliYunParameterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author tanghh
 * 阿里云的短信通知发送功能
 * @Date 2019/12/30 16:00
 */
@Service
@Slf4j
public class AliYunMessageServiceImpl implements AliYunMessageService {


    /**
     * @param phoneNumber 手机号
     * @param code        验证码
     * @return
     * @throws ClientException SendSmsResponse
     * @desc ：1.发送短信
     */
    @Override
    public SendSmsResponse sendSms(String phoneNumber, String code)  {
        SendSmsResponse sendSmsResponse = null;
        try{
            //1.准备好短信模板变量——验证码code
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", code);
            String TemplateParam = jsonObject.toJSONString();

            //2.可自助调整超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");

            //3.初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", AliYunParameterUtil.accessKeyId, AliYunParameterUtil.accessSecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", AliYunParameterUtil.PRODUCT, AliYunParameterUtil.DOMAIN);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            //4.组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(phoneNumber);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(AliYunParameterUtil.signName);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(AliYunParameterUtil.TemplateCode);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            request.setTemplateParam(TemplateParam);

            //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");

            //5.hint 此处可能会抛出异常，注意catch
            sendSmsResponse = acsClient.getAcsResponse(request);
        }catch (Exception e){
            log.error("发送阿里云短信失败",e);
        }
        return sendSmsResponse;
    }


    /**
     * @param bizId
     * @param phoneNumber
     * @return
     * @throws ClientException QuerySendDetailsResponse
     * @desc ：2.短信发送记录查询接口
     * 用于查询短信发送的状态，是否成功到达终端用户手机
     */
    @Override
    public QuerySendDetailsResponse querySendDetails(String bizId, String phoneNumber) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", AliYunParameterUtil.accessKeyId, AliYunParameterUtil.accessSecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", AliYunParameterUtil.PRODUCT, AliYunParameterUtil.DOMAIN);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber(phoneNumber);
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }
}
