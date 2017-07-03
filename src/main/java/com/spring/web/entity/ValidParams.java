package com.spring.web.entity;

import javax.validation.constraints.NotNull;

/**
 * Created by clj on 2017/7/3.
 * Description:
 */
public class ValidParams {
    @NotNull(message = "name can not be null")
    private String name;

    @NotNull(message = "sex can not be null")
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
