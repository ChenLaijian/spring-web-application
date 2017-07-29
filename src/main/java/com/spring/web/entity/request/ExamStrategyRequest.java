package com.spring.web.entity.request;

import com.alibaba.fastjson.JSONArray;
import com.spring.web.entity.ExamTopicEntity;
import com.spring.web.entity.QuestionBankEntity;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by clj on 2017/7/29.
 * Description:用于接收创建、修改出卷策略的接口参数
 */
public class ExamStrategyRequest {
    @NotEmpty(message = "相关题库不能为空")
    private Set<QuestionBankEntity> question_banks;

    @NotNull(message = "合格分数不能为空")
    @Range(min = 0, max = 100, message = "合格分数必须在0-100之间")
    private Integer cutoff_score;

    @NotEmpty(message = "大题不能为空")
    private Set<ExamTopicEntity> exam_topics;

    @NotEmpty(message = "难度设置不能为空")
    private JSONArray difficulty_setting;

    public Set<QuestionBankEntity> getQuestion_banks() {
        return question_banks;
    }

    public void setQuestion_banks(Set<QuestionBankEntity> question_banks) {
        this.question_banks = question_banks;
    }

    public Integer getCutoff_score() {
        return cutoff_score;
    }

    public void setCutoff_score(Integer cutoff_score) {
        this.cutoff_score = cutoff_score;
    }

    public Set<ExamTopicEntity> getExam_topics() {
        return exam_topics;
    }

    public void setExam_topics(Set<ExamTopicEntity> exam_topics) {
        this.exam_topics = exam_topics;
    }

    public JSONArray getDifficulty_setting() {
        return difficulty_setting;
    }

    public void setDifficulty_setting(JSONArray difficulty_setting) {
        this.difficulty_setting = difficulty_setting;
    }
}
