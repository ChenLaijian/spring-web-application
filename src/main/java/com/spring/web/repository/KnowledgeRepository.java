package com.spring.web.repository;

import com.spring.web.entity.KnowledgeEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by clj on 2017/7/29.
 * Description:
 */
public interface KnowledgeRepository extends CrudRepository<KnowledgeEntity, Integer> {
}
