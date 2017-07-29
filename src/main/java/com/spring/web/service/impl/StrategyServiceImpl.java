package com.spring.web.service.impl;

import com.spring.web.entity.ExamStrategyEntity;
import com.spring.web.entity.ExamTopicDetailEntity;
import com.spring.web.entity.ExamTopicEntity;
import com.spring.web.entity.KnowledgeEntity;
import com.spring.web.entity.QuestionBankEntity;
import com.spring.web.entity.request.ExamStrategyRequest;
import com.spring.web.exception.ServiceException;
import com.spring.web.repository.ExamStrategyRepository;
import com.spring.web.repository.ExamTopicRepository;
import com.spring.web.repository.KnowledgeRepository;
import com.spring.web.repository.QuestionBankRepository;
import com.spring.web.service.StrategyService;
import com.spring.web.utils.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by clj on 2017/7/29.
 * Description:试卷策略业务实现类
 */
@Service
public class StrategyServiceImpl implements StrategyService {
    @Autowired
    ExamTopicRepository topicRepository;

    @Autowired
    ExamStrategyRepository strategyRepository;

    @Autowired
    QuestionBankRepository bankRepository;

    @Autowired
    KnowledgeRepository knowledgeRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED)
    public ExamStrategyEntity createExamStrategy(ExamStrategyRequest request) {
        ExamStrategyEntity examStrategy = new ExamStrategyEntity();
        //获取题库
        Set<QuestionBankEntity> questionBanks = request.getQuestion_banks();
        questionBanks = buildQuestionBank(questionBanks);

        //合格分数
        examStrategy.setCutoff_score(request.getCutoff_score());

        //大题 获取详情中的知识点校验知识点是否存在 获取总分
        Set<ExamTopicEntity> examTopics = request.getExam_topics();
        examTopics = buildExamTopicEntity(examTopics, null);

        //总分\题目总数
        buildBaseInfoForStrategy(examStrategy, examTopics);
        examStrategy.setDifficulty_setting(request.getDifficulty_setting().toJSONString());

        //保存策略
        examStrategy = strategyRepository.save(examStrategy); //保存获取id

        //保存topic
        for(ExamTopicEntity topicEntity : examTopics){
            Set<ExamTopicDetailEntity> detailEntities = new HashSet<>();
            detailEntities.addAll(topicEntity.getExam_topic_details());
            topicEntity.setStrategy_id(examStrategy.getId());
            topicEntity.setExam_topic_details(null);
            topicEntity = topicRepository.save(topicEntity);
            for(ExamTopicDetailEntity detailEntity :detailEntities){
                detailEntity.setTopic_id(topicEntity.getId());
            }
            topicEntity.setExam_topic_details(detailEntities);
            topicRepository.save(topicEntity);
        }

        //保存questionBank
        examStrategy.setQuestion_banks(questionBanks);
        for(ExamTopicEntity examTopicEntity:examTopics){
            examTopicEntity.setStrategy_id(examStrategy.getId());
        }
        examStrategy.setExam_topics(examTopics);

        return strategyRepository.save(examStrategy);
    }

    /**
     * @param examTopics 仅带有部分request参数的对象数组
     * @return 所有大题的总分
     */
    private Set<ExamTopicEntity> buildExamTopicEntity(Set<ExamTopicEntity> examTopics, Integer strategyId){
        int sortNumber = 0;
        for(ExamTopicEntity examTopic : examTopics){
            Set<ExamTopicDetailEntity> examTopicDetails = examTopic.getExam_topic_details();
            List<Integer> knowledgeIds = new ArrayList<>(examTopicDetails.size());
            int questionCount = 0;

            if(strategyId!=null){
                examTopic.setStrategy_id(strategyId);
            }
            for(ExamTopicDetailEntity examTopicDetail : examTopicDetails){

                knowledgeIds.add(examTopicDetail.getKnowledge_id());
                //获取知识点
                Set<KnowledgeEntity> knowledge = IterableUtils.Iterable2Set(
                        knowledgeRepository.findAll(knowledgeIds)
                );
                if(knowledge.size() != knowledgeIds.size()){
                    throw new ServiceException("输入大题的知识点有误");
                }
                //计算题目数量
                questionCount += examTopicDetail.getQuestion_count();
            }
            //大题数目
            examTopic.setQuestion_count(questionCount);
            //大题排序
            examTopic.setSort_number(sortNumber ++);
        }
        return examTopics;
    }

    private Set<QuestionBankEntity> buildQuestionBank(Set<QuestionBankEntity> questionBanks){
        int size = questionBanks.size();
        List<Integer> questionBankIds = new ArrayList<>(size);
        for(QuestionBankEntity questionBank : questionBanks){
            questionBankIds.add(questionBank.getId());
        }

        questionBanks = IterableUtils.Iterable2Set(
                bankRepository.findAll(questionBankIds)
        );

        if(questionBanks.size() != size){
            throw new ServiceException("输入的题库有误");
        }
        return questionBanks;
    }

    private ExamStrategyEntity buildBaseInfoForStrategy(ExamStrategyEntity strategyEntity, Set<ExamTopicEntity> examTopicEntities){
        int totalScore = 0;
        int questionCount = 0;
        for(ExamTopicEntity examTopicEntity:examTopicEntities){
            totalScore += examTopicEntity.getTotalScore();
            questionCount += examTopicEntity.getQuestion_count();
        }
        strategyEntity.setTotal_score(totalScore);
        strategyEntity.setQuestion_count(questionCount);
        return strategyEntity;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED)
    public ExamStrategyEntity updateExamStrategy(int strategyId, ExamStrategyRequest request) {
        strategyRepository.delete(strategyId);
        ExamStrategyEntity examStrategy = new ExamStrategyEntity();
        //获取题库
        Set<QuestionBankEntity> questionBanks = request.getQuestion_banks();
        questionBanks = buildQuestionBank(questionBanks);

        //合格分数
        examStrategy.setCutoff_score(request.getCutoff_score());

        //大题 获取详情中的知识点校验知识点是否存在 获取总分
        Set<ExamTopicEntity> examTopics = request.getExam_topics();
        examTopics = buildExamTopicEntity(examTopics, null);

        //总分\题目总数
        buildBaseInfoForStrategy(examStrategy, examTopics);
        examStrategy.setDifficulty_setting(request.getDifficulty_setting().toJSONString());

        //保存策略
        examStrategy.setId(strategyId);
        examStrategy = strategyRepository.save(examStrategy); //保存获取id

        //保存topic
        for(ExamTopicEntity topicEntity : examTopics){
            Set<ExamTopicDetailEntity> detailEntities = new HashSet<>();
            detailEntities.addAll(topicEntity.getExam_topic_details());
            topicEntity.setStrategy_id(examStrategy.getId());
            topicEntity.setExam_topic_details(null);
            topicEntity = topicRepository.save(topicEntity);
            for(ExamTopicDetailEntity detailEntity :detailEntities){
                detailEntity.setTopic_id(topicEntity.getId());
            }
            topicEntity.setExam_topic_details(detailEntities);
            topicRepository.save(topicEntity);
        }

        //保存questionBank
        examStrategy.setQuestion_banks(questionBanks);
        for(ExamTopicEntity examTopicEntity:examTopics){
            examTopicEntity.setStrategy_id(examStrategy.getId());
        }
        examStrategy.setExam_topics(examTopics);

        return strategyRepository.save(examStrategy);
    }

    @Override
    public ExamStrategyEntity getExamStrategy(int strategyId) {
        return strategyRepository.findOne(strategyId);
    }

    @Override
    public void deleteExamStrategy(int strategyId){
        jdbcTemplate.execute("DELETE FROM t_exam_strategy where id = 8");
    }
}
