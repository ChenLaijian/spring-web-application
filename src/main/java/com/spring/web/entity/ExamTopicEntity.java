package com.spring.web.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * Created by clj on 2017/7/29.
 * Description:
 */
@Entity(name = "t_exam_topic")
public class ExamTopicEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer strategy_id;

    private String name;

    private Integer question_type;

    private Integer question_count;

    private Integer single_score;

    private Integer sort_number;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "topic_id", referencedColumnName = "id")
    private Set<ExamTopicDetailEntity> exam_topic_details;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStrategy_id() {
        return strategy_id;
    }

    public void setStrategy_id(Integer strategy_id) {
        this.strategy_id = strategy_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(Integer question_type) {
        this.question_type = question_type;
    }

    public Integer getQuestion_count() {
        return question_count;
    }

    public void setQuestion_count(Integer question_count) {
        this.question_count = question_count;
    }

    public Integer getSingle_score() {
        return single_score;
    }

    public void setSingle_score(Integer single_score) {
        this.single_score = single_score;
    }

    public Integer getSort_number() {
        return sort_number;
    }

    public void setSort_number(Integer sort_number) {
        this.sort_number = sort_number;
    }

    public Set<ExamTopicDetailEntity> getExam_topic_details() {
        return exam_topic_details;
    }

    public void setExam_topic_details(Set<ExamTopicDetailEntity> exam_topic_details) {
        this.exam_topic_details = exam_topic_details;
    }

    public int getTotalScore(){
        return single_score * question_count;
    }
}
