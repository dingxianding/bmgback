package com.example.repository;

import com.example.entity.Modell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * 车型Repository类
 */
public interface ModellRepository extends JpaRepository<Modell, Integer>, JpaSpecificationExecutor<Modell> {

    Modell findByIdAndDeleteTime(int id, Date deleteTime);

    Modell findModellByName(String name);

    @Transactional
    @Modifying
    @Query("update Modell tb set tb.deleteTime = now() where tb.id =:id")
    public void deleteById(@Param("id") int id);
}
