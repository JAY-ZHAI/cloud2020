package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.OpenFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Autowired
    private OpenFeignService openFeignService;

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable(value = "id") Long id){
        return openFeignService.getPaymentById(id);
    }

    @GetMapping(value = "/consumer/payment/timeout/get/{id}")
    public CommonResult<Payment> getPaymentTimeOut(@PathVariable(value = "id") Long id){
        return openFeignService.getPaymentTimeOutById(id);
    }


}
