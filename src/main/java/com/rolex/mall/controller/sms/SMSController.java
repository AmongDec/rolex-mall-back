package com.rolex.mall.controller.sms;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.rolex.mall.mapper.login.UserMapper;
import com.rolex.mall.pojo.sms.SMSVO;
import com.rolex.mall.service.AliYunMessageService;
import com.rolex.mall.util.CheckSumBuilder;
import com.rolex.mall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping("/sms")
public class SMSController {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private AliYunMessageService aliYunMessageServiceImpl;

    @Autowired
    private UserMapper userMapper;

    /**
     * 使用阿里云发送手机验证码功能
     */
    @RequestMapping (value = "/send" , method = RequestMethod.POST)
    public HashMap<String, Object> sendMessageToIphone(@RequestBody SMSVO smsvo) throws InterruptedException, ClientException {
        //1.准备好请求参数：phoneNumber、TemplateParam
        if(smsvo.getPhonenum() == null || smsvo.getPhonenum() ==""){
            return Result.parse("请求参数为空", Result.ERROR_CODE);
        }
        //随机生成手机验证码
        String code = CheckSumBuilder.getCheckSum();
        //2.调用接口，发短信
        SendSmsResponse response = aliYunMessageServiceImpl.sendSms(smsvo.getPhonenum(), code);
        Thread.sleep(3000L);
        System.out.println("response.getCode()" + response.getCode());
        //查询发送消息接口记录
        if (response.getCode() != null && response.getCode().equals("OK")) {
            QuerySendDetailsResponse querySendDetailsResponse = aliYunMessageServiceImpl.querySendDetails(response.getBizId(), smsvo.getPhonenum());
            //对返回结果true 或者false进行一个全局判断
            String responseCode = querySendDetailsResponse.getCode();
            String responseMessage = querySendDetailsResponse.getMessage();
            //OK代表信息发送成功
            if ("OK".equals(responseCode) && "OK".equals(responseMessage)) {
                //将code 保存到redis中,设置60s的过期时间
                redisTemplate.opsForValue().set(smsvo.getPhonenum()+"_code",code,120, TimeUnit.SECONDS);
                //并且返回给前端,方便前端用来判断
                return Result.parse("发送成功", Result.SUCCESS_CODE);
            }
        }
        return Result.parse("发送失败", Result.ERROR_CODE);
    }

    @PostMapping("/val")
    public HashMap<String, Object> valphone(@RequestBody SMSVO smsvo){
        if(smsvo == null || smsvo.getPhonenum() == null ){
            return Result.parse("请求参数为空", Result.ERROR_CODE);
        }

        if(redisTemplate.opsForValue().get(smsvo.getPhonenum()+"_code") == null){
            return Result.parse("验证码已过期或不存在", Result.ERROR_CODE);
        }

        String code = redisTemplate.opsForValue().get(smsvo.getPhonenum()+"_code").toString();

        if(code.equals(smsvo.getCode())){
            //根据手机号码更改密码
            userMapper.updateByPhone(smsvo.getPhonenum() , smsvo.getPassword());
            return Result.parse("修改成功", Result.SUCCESS_CODE);
        }else{
            return Result.parse("验证码错误", Result.ERROR_CODE);
        }
    }
}
