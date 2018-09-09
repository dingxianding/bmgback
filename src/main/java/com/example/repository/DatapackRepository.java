package com.example.repository;

import com.example.entity.Datapack;
import com.example.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 数据包类型Repository类
 */
public interface DatapackRepository extends JpaRepository<Datapack, Integer>, JpaSpecificationExecutor<Datapack> {

    List<Datapack> findByLevel(int level);

    @Query("SELECT t FROM Datapack t WHERE t.level=2 and t.parentId in (select d.id from Datapack d where d.parentId= :packId)  ORDER BY t.id ASC")
    List<Datapack> findAttrByPackId(@Param("packId") int packId);
}
