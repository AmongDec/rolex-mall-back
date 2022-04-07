package com.rolex.mall.controller.pay;


import com.alipay.api.AlipayApiException;
import com.rolex.mall.pojo.pay.AlipayBean;
import com.rolex.mall.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/pay")
public class AliPayController {
    @Autowired
    private PayService payServiceImpl;

    /**
     * 阿里支付
     * @return
     * @throws AlipayApiException
     */
    @PostMapping(value = "/alipay")
    public String alipay() throws AlipayApiException {
        System.out.println("jjjjjjjjjjjjjj");
        AlipayBean alipayBean = new AlipayBean();
        alipayBean.setOut_trade_no("212225584");
        alipayBean.setSubject("wweqebiaodd");
        alipayBean.setTotal_amount("500.0");
        alipayBean.setBody("一块腕表");
        String result = payServiceImpl.aliPay(alipayBean);
        System.out.println(result);
        return result;
    }

}