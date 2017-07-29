package com.spring.web.repository;

import com.spring.web.entity.QuestionEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by clj on 2017/7/29.
 * Description:
 */
public interface QuestionRepository extends CrudRepository<QuestionEntity, Integer> {
}
