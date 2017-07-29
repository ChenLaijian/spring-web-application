package com.spring.web.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by clj on 2017/7/29.
 * Description:
 */
@Entity(name = "t_knowledge_hierarchy")
public class KnowledgeHierarchyEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;
}
