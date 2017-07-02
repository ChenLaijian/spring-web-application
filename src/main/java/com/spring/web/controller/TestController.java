package com.spring.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.spring.web.exception.ServiceException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by clj on 2017/7/2.
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/exception")
    public JSONObject restException(){
        throw new ServiceException("这是一个业务类异常");
    }
}
