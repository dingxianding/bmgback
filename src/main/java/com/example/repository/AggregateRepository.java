package com.example.repository;

import com.example.entity.Aggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 动力总成Repository类
 */
public interface AggregateRepository extends JpaRepository<Aggregate, Integer>, JpaSpecificationExecutor<Aggregate> {

    Aggregate findByName(String name);
}
