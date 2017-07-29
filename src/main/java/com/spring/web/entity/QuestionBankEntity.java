package com.spring.web.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by clj on 2017/7/29.
 * Description:
 */
@Entity(name = "t_question_bank")
public class QuestionBankEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private Integer question_count;

/*    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "t_r_question_bank_and_question", joinColumns = {
            @JoinColumn(name = "question_bank_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "question_id", referencedColumnName = "id")
    })
    private Set<QuestionEntity> questions;*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuestion_count() {
        return question_count;
    }

    public void setQuestion_count(Integer question_count) {
        this.question_count = question_count;
    }
/*
    public Set<QuestionEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<QuestionEntity> questions) {
        this.questions = questions;
    }*/
}
