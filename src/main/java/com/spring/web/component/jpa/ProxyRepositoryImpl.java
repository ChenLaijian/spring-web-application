package com.spring.web.component.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Created by clj on 2017/7/3.
 * Description:
 */
@NoRepositoryBean
public class ProxyRepositoryImpl<T, ID extends Serializable> extends
        SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {
    private static final Logger logger = LoggerFactory
            .getLogger(ProxyRepositoryImpl.class);

    /**
     * The entity manager.
     */
    private final EntityManager entityManager;

    /**
     * The entity manager.
     */
    private final Class<T> domainClass;


    public ProxyRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        logger.debug("ProxyRepositoryImpl");
        entityManager = em;
        this.domainClass = domainClass;

    }
}



