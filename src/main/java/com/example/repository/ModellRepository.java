package com.example.repository;

import com.example.entity.Modell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 车型Repository类
 */
public interface ModellRepository extends JpaRepository<Modell, Integer>, JpaSpecificationExecutor<Modell> {

    Modell findModellByName(String name);
}
