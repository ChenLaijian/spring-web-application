package com.spring.web.repository;

import com.spring.web.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by clj on 2017/5/22.
 * Description:
 */
public interface UserRepository extends CrudRepository<User, String> {
}
