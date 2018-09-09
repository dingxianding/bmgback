package com.example.repository;

import com.example.entity.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 平台Repository类
 */
public interface PlatformRepository extends JpaRepository<Platform, Integer>, JpaSpecificationExecutor<Platform> {
    Platform findByName(String name);

}
