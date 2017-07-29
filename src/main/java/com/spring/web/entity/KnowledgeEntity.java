package com.spring.web.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by clj on 2017/7/29.
 * Description:
 */
@Entity(name = "t_knowledge")
public class KnowledgeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer knowledge_hierarchy_id;

    private String name;

    private Integer sort_number;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKnowledge_hierarchy_id() {
        return knowledge_hierarchy_id;
    }

    public void setKnowledge_hierarchy_id(Integer knowledge_hierarchy_id) {
        this.knowledge_hierarchy_id = knowledge_hierarchy_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort_number() {
        return sort_number;
    }

    public void setSort_number(Integer sort_number) {
        this.sort_number = sort_number;
    }
}
