package com.rolex.mall.service;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

public interface AliYunMessageService {
    public SendSmsResponse sendSms(String phoneNumber, String code);
    public QuerySendDetailsResponse querySendDetails(String bizId, String phoneNumber) throws ClientException;
}
