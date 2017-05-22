package com.spring.web.controller.demo;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by clj on 2017/5/22.
 * Description:
 */
@RestController
@RequestMapping("/rest")
public class RestfulController {
    @RequestMapping("/string")
    public String getString(){
        return "hello world!";
    }

    @RequestMapping("/json")
    public JSONObject getJSON(){
        JSONObject json = new JSONObject();
        json.put("hello", "world");
        return json;
    }
}
