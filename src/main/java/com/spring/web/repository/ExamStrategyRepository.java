package com.spring.web.repository;

import com.spring.web.entity.ExamStrategyEntity;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ExamStrategyRepository extends CrudRepository<ExamStrategyEntity, Integer> {

}
