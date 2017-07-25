package com.spring.web.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * Created by clj on 2017/7/3.
 * Description:配合Valid进行参数校验
 */
public class ValidatorUtil {
    public static String getErrorMessageStr(BindingResult result) {
        StringBuilder errorMessage = new StringBuilder();
        if (result.hasErrors()) {
            for (FieldError fieldError : result.getFieldErrors()) {
                errorMessage.append(",");
                errorMessage.append(fieldError.getDefaultMessage());
            }
        }
        return errorMessage.deleteCharAt(0).toString();
    }
}
