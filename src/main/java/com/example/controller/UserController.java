package com.example.controller;

import com.example.constant.ConstantKey;
import com.example.constant.RoleEnum;
import com.example.dto.*;
import com.example.entity.Aggregate;
import com.example.entity.Modell;
import com.example.entity.Teil;
import com.example.entity.User;
import com.example.exception.BaseException;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import com.example.utils.JwtGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.OutputStream;
import java.util.*;

/**
 * 人员控制器
 */
@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    protected BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtGenerator jwtGenerator;

    @GetMapping("")
    public List<User> getAll() {
        return repository.findAll();
    }

    @GetMapping("get/{id}")
    public User get(@PathVariable int id) {
        return repository.findOne(id);
    }

    /**
     * 获取分页的数据，带查询条件、排序、分页
     *
     * @param params
     * @return
     */
    @GetMapping("pagedList")
    public PageResultDTO pagedList(UserPageQueryDTO params) {

        // 动态查询条件
        Specification<User> spec = (root, query, cb) -> {
            List<Predicate> predicate = new ArrayList<>();

            if (params.getNumber() != null) {
                predicate.add(cb.like(cb.lower(root.get("number")), "%" + params.getNumber().toLowerCase() + "%"));
            }
            if (params.getName() != null) {
                predicate.add(cb.like(cb.lower(root.get("name")), "%" + params.getName().toLowerCase() + "%"));
            }
            if (params.getRole() != null) {
                predicate.add(cb.equal(root.get("role"), params.getRole()));
            }
            predicate.add(cb.isNull(root.get("deleteTime")));

            Predicate[] pre = new Predicate[predicate.size()];
            query.where(predicate.toArray(pre));

            return null;
        };
        if (params.getCurrentPage() == 0) {
            params.setCurrentPage(1);
        }
        if (params.getPageSize() == 0) {
            params.setPageSize(10);
        }
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        if (params.getSorter() != null) {
            String sorter = params.getSorter();
            String sortCol = sorter.substring(0, sorter.lastIndexOf("_"));
            String sortDirection = sorter.substring(sorter.lastIndexOf("_") + 1);
            if (sortDirection.equals("descend")) {
                sort = new Sort(Sort.Direction.DESC, sortCol);
            } else {
                sort = new Sort(Sort.Direction.ASC, sortCol);
            }
        }
        Pageable pageable = new PageRequest(params.getCurrentPage() - 1, params.getPageSize(), sort);
        Page<User> pageResult = repository.findAll(spec, pageable);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setList(pageResult.getContent());

        Pagination pagination = new Pagination();
        pagination.setTotal((int) pageResult.getTotalElements());
        pagination.setCurrent(params.getCurrentPage());
        pagination.setPageSize(params.getPageSize());
        pageResultDTO.setPagination(pagination);

        // 返回分页数据
        return pageResultDTO;
    }

    /**
     * 添加
     *
     * @param addDTO
     * @return
     */
    @PostMapping("")
    public User save(@RequestBody UserAddUpdateDTO addDTO) {
        //TODO 权限控制
        int currentUserID = UserService.getCurrentUserID();
        int currentUserRole = UserService.getCurrentUserRole();

        User entity = new User();
        entity.setNumber(addDTO.getNumber());
        entity.setName(addDTO.getName());
        entity.setUsername(addDTO.getUsername());

        String defaultPassword = "111";
        String md5Password = DigestUtils.md5DigestAsHex(defaultPassword.getBytes());
        String bCryptPassword = bCryptPasswordEncoder.encode(md5Password);
        entity.setPassword(bCryptPassword);
        entity.setRole(addDTO.getRole());

        entity.setRemark(addDTO.getRemark());

        entity.setInUser(repository.findByIdAndDeleteTime(currentUserID, null));

        return repository.save(entity);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public void remove(@PathVariable int id) {
        //TODO 权限控制

        //输入验证
        User entity = repository.findByIdAndDeleteTime(id, null);
        if (entity == null) {
            throw new BaseException("要删除的用户不存在");
        }

        repository.deleteById(id);
    }

    /**
     * 修改
     *
     * @param updateDTO
     * @return
     */
    @PutMapping("")
    public User update(@RequestBody UserAddUpdateDTO updateDTO) {
        //TODO 权限控制

        //输入验证
        User entity = repository.findByIdAndDeleteTime(updateDTO.getId(), null);
        if (entity == null) {
            throw new BaseException("要更新的用户不存在");
        }

        entity.setNumber(updateDTO.getNumber());
        entity.setName(updateDTO.getName());
        entity.setUsername(updateDTO.getUsername());
        entity.setRole(updateDTO.getRole());
        entity.setRemark(updateDTO.getRemark());


        entity.setUpdateTime(new Date());

        return repository.save(entity);
    }


    @PostMapping(value = "/login")
    public Map<String, Object> login(@RequestBody @Valid LoginDTO login, HttpServletResponse response) {
        //验证用户名密码
        User user = repository.findByUsernameAndDeleteTime(login.getUsername(), null);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        if (!bCryptPasswordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("密码错误");
        }
        //返回用户信息+JWT
        String jwt = jwtGenerator.generate(user.getId().toString(), user.getRole());
        Map<String, Object> result = new HashMap<>();
        result.put("access_token", jwt);
        result.put("id", user.getId());
        result.put("role", user.getRole());
        return result;
    }

    @GetMapping("/authorityList")
    public List<String> authorityList() {
        List<String> authentication = getAuthentication();
        return authentication;
    }

    public List<String> getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> list = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : authorities) {
            list.add(grantedAuthority.getAuthority());
        }
        return list;
    }

    @GetMapping("current")
    public Map<String, Object> GetCurrentUser() {
        int currentUserID = UserService.getCurrentUserID();

        User entity = repository.findByIdAndDeleteTime(currentUserID, null);
        if (entity != null) {
            Map<String, Object> result = new HashMap<>();
            result.put("id", currentUserID);
            result.put("name", entity.getName());

            return result;
        } else {
            throw new UsernameNotFoundException("用户不存在");
        }
    }
}
