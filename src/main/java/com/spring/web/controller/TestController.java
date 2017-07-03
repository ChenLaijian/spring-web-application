package com.spring.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.spring.web.entity.ValidParams;
import com.spring.web.exception.ServiceException;
import com.spring.web.utils.ValidatorUtil;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    @RequestMapping(value = "/valid", method = RequestMethod.POST)
    public JSONObject paramValidateTest(@Valid @RequestBody ValidParams validParams, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ServiceException(ValidatorUtil.getErrorMessageStr(bindingResult));
        }
        return new JSONObject();
    }
}
