package com.spring.web.exception;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by clj on 2017/6/7.
 * Description:
 */
@ControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public JSONObject commonExceptionHandler(Exception ex){
        JSONObject json = new JSONObject();
        json.put("server_time", System.currentTimeMillis());
        json.put("code", "API/Error");
        json.put("message", "Error...");
        return json;
    }
}
