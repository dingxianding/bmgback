package com.example.controller;

import com.example.dto.*;
import com.example.entity.*;
import com.example.exception.BaseException;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 零件
 * <p>
 * 三分钟彻底了解Restful最佳实践
 * https://blog.csdn.net/chenxiaochan/article/details/73716617
 *
 * @RequestParam @RequestBody @PathVariable 等参数绑定注解详解
 * https://blog.csdn.net/walkerJong/article/details/7946109
 */
@RestController
@RequestMapping("teil")
public class TeilController {

    @Autowired
    private TeilRepository repository;

    @Autowired
    private AbgasstufeRepository abgasstufeRepository;

    @Autowired
    private ModellRepository modellRepository;

    @Autowired
    private AggregateRepository aggregateRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public List<Teil> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Teil get(@PathVariable int id) {
        return repository.findByIdAndDeleteTime(id, null);
    }

    /**
     * 获取分页的数据，带查询条件、排序、分页
     *
     * @param params
     * @return
     */
    @GetMapping("pagedList")
    public PageResultDTO pagedList(TeilPageQueryDTO params) {

        // 动态查询条件
        Specification<Teil> spec = (root, query, cb) -> {
            List<Predicate> predicate = new ArrayList<>();

            if (params.getNumber() != null) {
                predicate.add(cb.like(root.get("number"), "%" + params.getNumber() + "%"));
            }
            if (params.getName() != null) {
                predicate.add(cb.like(root.get("name"), "%" + params.getName() + "%"));
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
        Page<Teil> pageResult = repository.findAll(spec, pageable);

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
    public Teil save(@RequestBody TeilAddDTO addDTO) {
        //TODO 权限控制

        Teil entity = new Teil();
        entity.setNumber(addDTO.getNumber());
        entity.setName(addDTO.getName());
        entity.setLieferant(addDTO.getLieferant());
        entity.setBezugsart(addDTO.getBezugsart());

        entity.setAbgasstufe(abgasstufeRepository.findByName(addDTO.getAbgasstufe()));
        entity.setErstModell(modellRepository.findModellByName(addDTO.getErstModell()));
        entity.setErstAggregate(aggregateRepository.findByName(addDTO.getErstAggregate()));

        List<Modell> modells = new ArrayList<Modell>();
        for (String i : addDTO.getModells()) {
            modells.add(modellRepository.findModellByName(i));
        }
        entity.setModells(modells);

        List<Aggregate> aggregates = new ArrayList<Aggregate>();
        for (String i : addDTO.getAggregates()) {
            aggregates.add(aggregateRepository.findByName(i));
        }

        entity.setAggregates(aggregates);
        //TODO 用户管理完善
        entity.setInUser(userRepository.findByIdAndDeleteTime(1, null));
        entity.setFop(userRepository.findByIdAndDeleteTime(1, null));

        entity.setInTime(new Date());
        entity.setUpdateTime(new Date());
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
        Teil entity = repository.findByIdAndDeleteTime(id, null);
        if (entity == null) {
            throw new BaseException("要删除的零件不存在");
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
    public Teil update(@RequestBody TeilUpdateDTO updateDTO) {
        //TODO 权限控制

        //输入验证
        Teil entity = repository.findByIdAndDeleteTime(updateDTO.getId(), null);
        if (entity == null) {
            throw new BaseException("要更新的零件不存在");
        }

        entity.setNumber(updateDTO.getNumber());
        entity.setName(updateDTO.getName());
        entity.setLieferant(updateDTO.getLieferant());
        entity.setBezugsart(updateDTO.getBezugsart());

        entity.setAbgasstufe(abgasstufeRepository.findByName(updateDTO.getAbgasstufe()));
        entity.setErstModell(modellRepository.findModellByName(updateDTO.getErstModell()));
        entity.setErstAggregate(aggregateRepository.findByName(updateDTO.getErstAggregate()));

        List<Modell> modells = new ArrayList<Modell>();
        for (String i : updateDTO.getModells()) {
            modells.add(modellRepository.findModellByName(i));
        }
        entity.setModells(modells);

        List<Aggregate> aggregates = new ArrayList<Aggregate>();
        for (String i : updateDTO.getAggregates()) {
            aggregates.add(aggregateRepository.findByName(i));
        }

        entity.setAggregates(aggregates);
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
    public TeilInitDataDTO init(TeilPageQueryDTO params) {
        TeilInitDataDTO teilInitDataDTO = new TeilInitDataDTO();
        //排放阶段、车型、动力总成
        teilInitDataDTO.setAbgasstufeList(abgasstufeRepository.findAll());
        teilInitDataDTO.setModellList(modellRepository.findAll());
        teilInitDataDTO.setAggregateList(aggregateRepository.findAll());
        //分页数据
        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO = pagedList(params);
        teilInitDataDTO.setList(pageResultDTO.getList());
        teilInitDataDTO.setPagination(pageResultDTO.getPagination());
        return teilInitDataDTO;
    }


}
