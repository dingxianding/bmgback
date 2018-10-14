package com.example.repository;

import com.example.entity.Member;
import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * 人员Repository类
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    //是否删除都要查询，因为name唯一
    User findByName(String name);

    User findByIdAndDeleteTime(Integer id, Date deleteTime);

    User findByUsername(String username);

    @Transactional
    @Modifying
    @Query("update User tb set tb.deleteTime = now() where tb.id =:id")
    void deleteById(@Param("id") int id);

    User findByUsernameAndDeleteTime(String username,Date deleteTime);
}
