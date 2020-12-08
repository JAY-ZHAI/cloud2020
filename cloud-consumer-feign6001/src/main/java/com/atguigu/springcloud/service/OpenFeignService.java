package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.service.impl.OpenFeignFallBackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "cloud-payment-service",fallback = OpenFeignFallBackService.class)
public interface OpenFeignService {

    @GetMapping(value = "/payment/getPayment/{id}")
    public CommonResult getPaymentById(@PathVariable(value = "id") Long id);

    @GetMapping(value = "/payment/getPayment/timeout/{id}")
    public CommonResult getPaymentTimeOutById(@PathVariable(value = "id") Long id);

}
