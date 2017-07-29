package com.spring.web.controller;

import com.spring.web.entity.ExamStrategyEntity;
import com.spring.web.entity.request.ExamStrategyRequest;
import com.spring.web.exception.ServiceException;
import com.spring.web.service.StrategyService;
import com.spring.web.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by clj on 2017/7/29.
 * Description:
 */
@RestController
@RequestMapping(value = "/exam_strategies")
public class StrategiesController {

    @Autowired
    StrategyService strategyService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ExamStrategyEntity createExamStrategy(@RequestBody @Valid ExamStrategyRequest request, BindingResult bindingResult) {
        //参数的校验
        if (bindingResult.hasErrors()) {
            throw new ServiceException(ValidatorUtil.getErrorMessageStr(bindingResult));
        }
        //调用业务类，实现新增
        return strategyService.createExamStrategy(request);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ExamStrategyEntity updateExamStrategy(@RequestBody @Valid ExamStrategyRequest request, BindingResult bindingResult, @PathVariable("id") Integer id){
        //参数的校验
        if (bindingResult.hasErrors()) {
            throw new ServiceException(ValidatorUtil.getErrorMessageStr(bindingResult));
        }
        //调用业务类，实现新增
        return strategyService.updateExamStrategy(id, request);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ExamStrategyEntity getExamStrategy(@PathVariable("id") Integer id){
        return strategyService.getExamStrategy(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Boolean deleteExamStrategy(@PathVariable("id") Integer id){
        strategyService.deleteExamStrategy(id);
        return true;
    }

}
