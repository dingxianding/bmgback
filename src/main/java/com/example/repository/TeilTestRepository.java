package com.example.repository;

import com.example.entity.Teil;
import com.example.entity.TeilSchedule;
import com.example.entity.TeilTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;

/**
 *
 */
public interface TeilTestRepository extends JpaRepository<TeilTest, Integer>, JpaSpecificationExecutor<TeilTest> {
    TeilTest findByIdAndDeleteTime(int id, Date deleteTime);

    TeilTest findByTeilAndDeleteTime(Teil teil, Date deleteTime);

    @Transactional
    @Modifying
    @Query("update TeilTest tb set tb.deleteTime = now() where tb.id =:id")
    public void deleteById(@Param("id") int id);
}
