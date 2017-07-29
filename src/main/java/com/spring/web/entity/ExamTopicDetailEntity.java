package com.spring.web.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by clj on 2017/7/29.
 * Description:
 */
@Entity(name = "t_exam_topic_detail")
public class ExamTopicDetailEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer topic_id;

    private Integer knowledge_id;

    private Integer question_count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(Integer topic_id) {
        this.topic_id = topic_id;
    }

    public Integer getKnowledge_id() {
        return knowledge_id;
    }

    public void setKnowledge_id(Integer knowledge_id) {
        this.knowledge_id = knowledge_id;
    }

    public Integer getQuestion_count() {
        return question_count;
    }

    public void setQuestion_count(Integer question_count) {
        this.question_count = question_count;
    }
}
