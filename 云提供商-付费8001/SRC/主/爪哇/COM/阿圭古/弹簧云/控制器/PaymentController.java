package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
//@DefaultProperties(defaultFallback = "",commandProperties = {})
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverport;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result= paymentService.create(payment);
       log.info("******插入结果是："+result);
       if (result>0){
           return new CommonResult(200,"插入数据成功-----"+serverport,result);
       }else {
           return new CommonResult(444,"插入数据失败",null);
       }
    }

    @GetMapping(value = "/payment/getPayment/{id}")
    public CommonResult getPaymentById(@PathVariable(value = "id") Long id){
        Payment result= paymentService.getPaymentById(id);
        log.info("******查询结果是："+result);
        if (result!=null){
            return new CommonResult(200,"查询数据成功-----"+serverport,result);
        }else {
            return new CommonResult(444,"查询数据失败",null);
        }
    }

    @GetMapping(value = "/payment/getPayment/timeout/{id}")
    @HystrixCommand(fallbackMethod = "getPaymentTimeOutByIdHandler",
            commandProperties = {
            @HystrixProperty(name ="execution.isolation.thread.timeoutInMilliseconds",value = "3000"),//3s中以内就是正常逻辑
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),  //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),   //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),  //时间范围
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"), //失败率达到多少后跳闸
    })
    public CommonResult getPaymentTimeOutById(@PathVariable(value = "id") Long id){
        Payment result= paymentService.getPaymentById(id);
        log.info("******查询结果是："+result);
        if (result!=null){

            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return new CommonResult(200,"当前线程："+Thread.currentThread().getName()+" 查询数据成功-----"+serverport,result);
        }else {
            return new CommonResult(444,"查询数据失败",null);
        }
    }

    public CommonResult getPaymentTimeOutByIdHandler(@PathVariable(value = "id") Long id){
        Payment result= paymentService.getPaymentById(id);
        log.info("******查询结果是："+result);
        if (result!=null){
            return new CommonResult(200,"当前线程："+Thread.currentThread().getName()+" =======查询数据成功-----"+serverport,result);
        }else {
            return new CommonResult(444,"查询数据失败~~_~~",null);
        }
    }
}
