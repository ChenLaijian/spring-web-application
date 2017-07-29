package com.spring.web.service;

import com.spring.web.entity.ExamStrategyEntity;
import com.spring.web.entity.request.ExamStrategyRequest;

/**
 * Created by clj on 2017/7/29.
 * Description:
 */
public interface StrategyService {
    ExamStrategyEntity createExamStrategy(ExamStrategyRequest request);

    ExamStrategyEntity updateExamStrategy(int strategyId, ExamStrategyRequest request);

    ExamStrategyEntity getExamStrategy(int strategyId);

    void deleteExamStrategy(int strategyId);
}
