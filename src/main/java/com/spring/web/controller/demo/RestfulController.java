package com.spring.web.controller.demo;

import com.alibaba.fastjson.JSONObject;
import com.spring.web.entity.User;
import com.spring.web.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by clj on 2017/5/22.
 * Description:
 */
@RestController
@RequestMapping("/rest")
public class RestfulController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/exception")
    public String getException(){
        throw new ServiceException("this is a runtime exception");
    }

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

    @RequestMapping("/mysql")
    public JSONObject getMysqlContent(){
        String sql = "select id, name, age from user";
        JSONObject json = new JSONObject();

        //row map
        RowMapper<User> rowmap = new BeanPropertyRowMapper<User>(User.class);
        List<User> users = jdbcTemplate.query(sql, rowmap);
        System.out.println(users.toString());
        json.put("map", users.size());

        return json;
    }
}
