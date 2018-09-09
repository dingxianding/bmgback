package com.example.repository;

import com.example.entity.Abgasstufe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 排放阶段Repository类
 */
public interface AbgasstufeRepository extends JpaRepository<Abgasstufe, Integer>, JpaSpecificationExecutor<Abgasstufe> {
    Abgasstufe findByName(String name);
}
