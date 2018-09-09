package com.example.repository;

import com.example.entity.Member;
import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;

/**
 * 人员Repository类
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    //是否删除都要查询，因为name唯一
    User findByName(String name);

    User findByIdAndDeleteTime(Integer id, Date deleteTime);
}
