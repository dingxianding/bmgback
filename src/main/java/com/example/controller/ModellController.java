package com.example.controller;

import com.example.dto.*;
import com.example.entity.Aggregate;
import com.example.entity.FileEntity;
import com.example.entity.Modell;
import com.example.entity.Teil;
import com.example.exception.BaseException;
import com.example.repository.*;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 车型
 */
@RestController
@RequestMapping("modell")
public class ModellController {
    @Autowired
    private ModellRepository repository;

    @Autowired
    private PlatformRepository platformRepository;

    @Autowired
    private AggregateRepository aggregateRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileRepository fileRepository;

    @GetMapping("/{id}")
    public ModellWithTeilDTO get(@PathVariable int id) {
        ModellWithTeilDTO modellWithTeilDTO = new ModellWithTeilDTO();
        Modell modell = repository.findByIdAndDeleteTime(id, null);
        if (modell != null) {
            modellWithTeilDTO.setModell(modell);
            modellWithTeilDTO.setTeils(modell.getTeils());
        }
        return modellWithTeilDTO;
    }

    /**
     * 获取分页的数据，带查询条件、排序、分页
     *
     * @param params
     * @return
     */
    @GetMapping("pagedList")
    public PageResultDTO pagedList(ModellPageQueryDTO params) {

        // 动态查询条件
        Specification<Modell> spec = (root, query, cb) -> {
            List<Predicate> predicate = new ArrayList<>();

            if (params.getName() != null) {
                predicate.add(cb.like(cb.lower(root.get("name")), "%" + params.getName().toLowerCase() + "%"));
            }
            if (params.getPlatform() != null) {
                predicate.add(cb.equal(root.get("platform"), platformRepository.findByName(params.getPlatform())));
            }
            if (params.getAggregates().size() > 0) {
                //TODO 关联查询，直接查询会出错
//                List<Aggregate> aggregates = new ArrayList<Aggregate>();
//                for (String i : params.getAggregates()) {
//                    aggregates.add(aggregateRepository.findByName(i));
//                }
//                Expression<String> exp = root.get("aggregates");
//                predicate.add(exp.in(aggregates));
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
        Page<Modell> pageResult = repository.findAll(spec, pageable);

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
    public Modell save(@RequestBody ModellAddUpdateDTO addDTO) {
        int currentUserID = UserService.getCurrentUserID();
        int currentUserRole = UserService.getCurrentUserRole();

        Modell entity = new Modell();
        entity.setName(addDTO.getName());

        entity.setPlatform(platformRepository.findByName(addDTO.getPlatform()));
        List<Aggregate> aggregates = new ArrayList<Aggregate>();
        for (String i : addDTO.getAggregates()) {
            aggregates.add(aggregateRepository.findByName(i));
        }
        entity.setAggregates(aggregates);

        entity.setVffTime(addDTO.getVffTime());
        entity.setPvsTime(addDTO.getPvsTime());
        entity.setOsTbtTime(addDTO.getOsTbtTime());
        entity.setOsTime(addDTO.getOsTime());
        entity.setSopTbtTime(addDTO.getSopTbtTime());
        entity.setSopTime(addDTO.getSopTime());
        entity.setRunCount(addDTO.getRunCount());
        entity.setDescription(addDTO.getDescription());

        //跑车计划，文件
        List<FileEntity> fileEntitieList = new ArrayList<>();
        if (addDTO.getRunPlan() != null && addDTO.getRunPlan().size() > 0) {
            for (Integer i : addDTO.getRunPlan()) {
                fileEntitieList.add(fileRepository.findByIdAndDeleteTime(i, null));
            }
        }
        //是空的要置空
        entity.setRunPlan(fileEntitieList);

        entity.setInUser(userRepository.findByIdAndDeleteTime(currentUserID, null));
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
        Modell entity = repository.findByIdAndDeleteTime(id, null);
        if (entity == null) {
            throw new BaseException("要删除的车型不存在");
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
    public Modell update(@RequestBody ModellAddUpdateDTO updateDTO) {
        //输入验证
        Modell entity = repository.findByIdAndDeleteTime(updateDTO.getId(), null);
        if (entity == null) {
            throw new BaseException("要更新的车型不存在");
        }

        entity.setName(updateDTO.getName());

        entity.setPlatform(platformRepository.findByName(updateDTO.getPlatform()));

        List<Aggregate> aggregates = new ArrayList<Aggregate>();
        for (String i : updateDTO.getAggregates()) {
            aggregates.add(aggregateRepository.findByName(i));
        }
        entity.setAggregates(aggregates);

        entity.setVffTime(updateDTO.getVffTime());
        entity.setPvsTime(updateDTO.getPvsTime());
        entity.setOsTbtTime(updateDTO.getOsTbtTime());
        entity.setOsTime(updateDTO.getOsTime());
        entity.setSopTbtTime(updateDTO.getSopTbtTime());
        entity.setSopTime(updateDTO.getSopTime());
        entity.setRunCount(updateDTO.getRunCount());
        entity.setDescription(updateDTO.getDescription());

        //跑车计划，文件
        List<FileEntity> fileEntitieList = new ArrayList<>();
        if (updateDTO.getRunPlan() != null && updateDTO.getRunPlan().size() > 0) {
            for (Integer i : updateDTO.getRunPlan()) {
                fileEntitieList.add(fileRepository.findByIdAndDeleteTime(i, null));
            }
        }
        //是空的要置空
        entity.setRunPlan(fileEntitieList);

        entity.setUpdateTime(new Date());

        return repository.save(entity);
    }

    /**
     * 零件List界面的初始化数据
     * 在初始化的时候从数据库读取
     * 包括排放阶段、车型、动力总成以及分页的数据
     *
     * @return
     */
    @GetMapping("init")
    public ModelllInitDataDTO init(ModellPageQueryDTO params) {
        ModelllInitDataDTO result = new ModelllInitDataDTO();
        //排放阶段、车型、动力总成
        result.setPlatformList(platformRepository.findAll());
        result.setAggregateList(aggregateRepository.findAll());
        //分页数据
        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO = pagedList(params);
        result.setList(pageResultDTO.getList());
        result.setPagination(pageResultDTO.getPagination());
        return result;
    }


}
