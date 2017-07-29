package com.spring.web.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * Created by clj on 2017/7/29.
 * Description:
 */
@Entity(name = "t_exam_strategy")
public class ExamStrategyEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private Integer question_count;

    private Integer cutoff_score;

    private Integer total_score;

    private String difficulty_setting;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "t_r_exam_strategy_and_question_bank", joinColumns = {
            @JoinColumn(name = "strategy_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "question_bank_id", referencedColumnName = "id")
    })
    Set<QuestionBankEntity> question_banks;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="strategy_id")
    Set<ExamTopicEntity> exam_topics;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestion_count() {
        return question_count;
    }

    public void setQuestion_count(Integer question_count) {
        this.question_count = question_count;
    }

    public Integer getCutoff_score() {
        return cutoff_score;
    }

    public void setCutoff_score(Integer cutoff_score) {
        this.cutoff_score = cutoff_score;
    }

    public String getDifficulty_setting() {
        return difficulty_setting;
    }

    public void setDifficulty_setting(String difficulty_setting) {
        this.difficulty_setting = difficulty_setting;
    }

    public Set<QuestionBankEntity> getQuestion_banks() {
        return question_banks;
    }

    public void setQuestion_banks(Set<QuestionBankEntity> question_banks) {
        this.question_banks = question_banks;
    }

    public Set<ExamTopicEntity> getExam_topics() {
        return exam_topics;
    }

    public void setExam_topics(Set<ExamTopicEntity> exam_topics) {
        this.exam_topics = exam_topics;
    }

    public Integer getTotal_score() {
        return total_score;
    }

    public void setTotal_score(Integer total_score) {
        this.total_score = total_score;
    }
}
