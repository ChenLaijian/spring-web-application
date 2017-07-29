package com.spring.web.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by clj on 2017/7/29.
 * Description:
 */
@Entity(name = "t_question")
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String body;

    private Integer difficulty;

    private Integer question_type;

/*
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "t_r_question_and_knowledge", joinColumns = {
            @JoinColumn(name = "question_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "knowledge_id", referencedColumnName = "id")
    })
    private Set<KnowledgeEntity> knowledges;
*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(Integer question_type) {
        this.question_type = question_type;
    }
}
