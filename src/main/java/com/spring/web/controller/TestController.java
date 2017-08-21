package com.spring.web.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by clj on 2017/8/21.
 * Description:
 */
@RestController
@RequestMapping("logger/test")
public class TestController {
    private final Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("")
    public Object loggerTest(){


        RuntimeException runtimeException = new RuntimeException("this is a runtime error");

        logger.debug("error occurred {}",runtimeException.getMessage());

        logger.debug("error occurred {}, {}", "str1", "str2");

        logger.debug("error occurred{}, {}", "str1", runtimeException);

        logger.debug("error occurred{}", "str1", runtimeException);

        return null;
    }
}
