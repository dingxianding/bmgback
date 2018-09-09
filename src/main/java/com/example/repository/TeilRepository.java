package com.example.repository;

import com.example.entity.Teil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 零件Repository类
 */
public interface TeilRepository extends JpaRepository<Teil, Integer>, JpaSpecificationExecutor<Teil> {
    @Transactional
    @Modifying
    @Query("update Teil tb set tb.deleteTime = now() where tb.id in :ids")
    public void deleteByIds(@Param("ids") List<Integer> ids);
}
