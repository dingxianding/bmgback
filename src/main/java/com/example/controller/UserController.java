package com.example.controller;

import com.example.dto.ExecuteDTO;
import com.example.dto.PageQueryParamDTO;
import com.example.dto.PageResultDTO;
import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 人员控制器
 */
@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping("")
    public List<User> getAll() {
        return repository.findAll();
    }

    @GetMapping("get/{id}")
    public User get(@PathVariable int id) {
        return repository.findOne(id);
    }

    @PostMapping("save")
    public ExecuteDTO save(@RequestBody User entity) {
        repository.save(entity);
        return new ExecuteDTO(true, "保存成功", entity.getId());
    }

    @PostMapping("loadPage")
    public PageResultDTO loadPage(@RequestBody PageQueryParamDTO params) {

//        // 动态查询条件
//        Specification<User> spec = (root, query, cb) -> {
//            if (params.getQuery() != null) {
//                // 筛选 会员姓名
//                query.where(cb.equal(root.get("name"), params.getQuery().toString()));
//            }
//
//            return null;
//        };
//        Pageable pageable = new PageRequest(params.getCurrent() - 1, params.getPageSize());
//        Page<User> pageResult = repository.findAll(spec, pageable);
//
//        // 返回分页数据
//        return new PageResultDTO(pageResult.getTotalElements(), pageResult.getContent());
//
        return null;
    }

    @GetMapping("remove/{id}")
    @DeleteMapping
    public ExecuteDTO remove(@PathVariable int id) {
        repository.delete(id);
        return new ExecuteDTO(true, "删除成功", id);
    }

}
