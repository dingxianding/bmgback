package com.example.repository;

import com.example.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * 文件Repository类
 */
public interface FileRepository extends JpaRepository<FileEntity, Integer>, JpaSpecificationExecutor<FileEntity> {

    FileEntity findByIdAndDeleteTime(int id, Date deleteTime);

    FileEntity findFileByName(String name);

    @Transactional
    @Modifying
    @Query("update FileEntity tb set tb.deleteTime = now() where tb.id =:id")
    public void deleteById(@Param("id") int id);
}
