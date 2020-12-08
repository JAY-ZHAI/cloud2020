package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.service.OpenFeignService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OpenFeignFallBackService implements OpenFeignService {

    @Override
    public CommonResult getPaymentById(Long id) {

        return  new CommonResult(404,"-----PaymentFallbackService fall back-paymentInfo_OK , (┬＿┬)");
    }

    @Override
    public CommonResult getPaymentTimeOutById(Long id) {
        return  new CommonResult(404,"-----PaymentFallbackService fall back-paymentInfo_TimeOut , (┬＿┬)");
    }
}
