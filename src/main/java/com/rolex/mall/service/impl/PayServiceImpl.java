package com.rolex.mall.service.impl;

import com.alipay.api.AlipayApiException;
import com.rolex.mall.pojo.pay.AlipayBean;
import com.rolex.mall.service.PayService;
import com.rolex.mall.util.Alipay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private Alipay alipay;

    @Override
    public String aliPay(AlipayBean alipayBean) throws AlipayApiException {
        return alipay.pay(alipayBean);
    }
}
