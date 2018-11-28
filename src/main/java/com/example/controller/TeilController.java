package com.example.controller;

import com.example.constant.RoleEnum;
import com.example.dto.*;
import com.example.entity.*;
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

import javax.persistence.AttributeOverride;
import javax.persistence.criteria.*;
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
@RequestMapping("myapi/teil")
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

    @Autowired
    private TeilScheduleRepository teilScheduleRepository;

    @Autowired
    private TeilTestRepository teilTestRepository;

    @GetMapping("")
    public List<Teil> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public TeilFullInfoDTO get(@PathVariable int id) {
        TeilFullInfoDTO teilFullInfoDTO = new TeilFullInfoDTO();
        Teil teil = repository.findByIdAndDeleteTime(id, null);
        if (teil != null) {
            teilFullInfoDTO.setTeil(teil);
            TeilSchedule teilSchedule = teilScheduleRepository.findByTeilAndDeleteTime(teil, null);
            if (teilSchedule == null) {
                //一定要设置一个，不然返回为空，前端不好处理
                teilSchedule = new TeilSchedule();
                teilSchedule.setTeil(teil);
            }
            teilFullInfoDTO.setTeilSchedule(teilSchedule);
            TeilTest teilTest = teilTestRepository.findByTeilAndDeleteTime(teil, null);
            if (teilTest == null) {
                teilTest = new TeilTest();
                teilTest.setTeil(teil);
            }
            teilFullInfoDTO.setTeilTest(teilTest);
        }

        return teilFullInfoDTO;
    }

    /**
     * 只获取自己的零件
     * 获取分页的数据，带查询条件、排序、分页
     *
     * @param params
     * @return
     */
    @GetMapping("pagedList")
    public PageResultDTO pagedList(TeilPageQueryDTO params) {
        int currentUserID = UserService.getCurrentUserID();
        int currentUserRole = UserService.getCurrentUserRole();

        // 动态查询条件
        Specification<Teil> spec = new Specification<Teil>() {
            @Override
            public Predicate toPredicate(Root<Teil> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<Predicate>();

                if (params.getNumber() != null) {
                    predicate.add(cb.like(cb.lower(root.<String>get("number")), "%" + params.getNumber().toLowerCase() + "%"));
                }
                if (params.getName() != null) {
                    predicate.add(cb.like(cb.lower(root.<String>get("name")), "%" + params.getName().toLowerCase() + "%"));
                }
                if (params.getStatus() != null) {
                    predicate.add(cb.equal(root.get("status"), params.getStatus()));
                }
                //适用车型
                if (params.getModell() != null) {
                    Join<Modell, Teil> join = root.join("modells", JoinType.LEFT);
                    predicate.add(cb.equal(join.get("name"), params.getModell()));
                }
                //适用动力总成
                if (params.getAggregate() != null) {
                    Join<Aggregate, Teil> join = root.join("aggregates", JoinType.LEFT);
                    predicate.add(cb.equal(join.get("name"), params.getAggregate()));
                }
                //fop
                if (params.getFop() != null) {
                    Join<User, Teil> join = root.join("fop", JoinType.LEFT);
                    predicate.add(cb.like(cb.lower(join.<String>get("name")), "%" + params.getFop().toLowerCase() + "%"));
                }
                //只能查看自己的
                Join<User, Teil> join = root.join("fop", JoinType.LEFT);
                predicate.add(cb.equal(join.get("id"), currentUserID));
                predicate.add(cb.isNull(root.get("deleteTime")));

                Predicate[] pre = new Predicate[predicate.size()];
                query.where(predicate.toArray(pre));

                return query.where(predicate.toArray(new Predicate[predicate.size()])).getRestriction();
            }
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
     * 获取所有人的零件
     *
     * @param params
     * @return
     */
    @GetMapping("pagedListAll")
    public PageResultDTO pagedListAll(TeilPageQueryDTO params) {
        int currentUserID = UserService.getCurrentUserID();
        int currentUserRole = UserService.getCurrentUserRole();

        // 动态查询条件
        Specification<Teil> spec = new Specification<Teil>() {
            @Override
            public Predicate toPredicate(Root<Teil> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<Predicate>();

                if (params.getNumber() != null) {
                    predicate.add(cb.like(cb.lower(root.<String>get("number")), "%" + params.getNumber().toLowerCase() + "%"));
                }
                if (params.getName() != null) {
                    predicate.add(cb.like(cb.lower(root.<String>get("name")), "%" + params.getName().toLowerCase() + "%"));
                }
                if (params.getStatus() != null) {
                    predicate.add(cb.equal(root.get("status"), params.getStatus()));
                }
                //适用车型
                if (params.getModell() != null) {
                    Join<Modell, Teil> join = root.join("modells", JoinType.LEFT);
                    predicate.add(cb.equal(join.get("name"), params.getModell()));
                }
                //适用动力总成
                if (params.getAggregate() != null) {
                    Join<Aggregate, Teil> join = root.join("aggregates", JoinType.LEFT);
                    predicate.add(cb.equal(join.get("name"), params.getAggregate()));
                }
                //fop
                if (params.getFop() != null) {
                    Join<User, Teil> join = root.join("fop", JoinType.LEFT);
                    predicate.add(cb.like(cb.lower(join.<String>get("name")), "%" + params.getFop().toLowerCase() + "%"));
                }
                predicate.add(cb.isNull(root.get("deleteTime")));

                Predicate[] pre = new Predicate[predicate.size()];
                query.where(predicate.toArray(pre));

                return query.where(predicate.toArray(new Predicate[predicate.size()])).getRestriction();
            }
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
        int currentUserID = UserService.getCurrentUserID();
        int currentUserRole = UserService.getCurrentUserRole();

        Teil entity = new Teil();
        entity.setNumber(addDTO.getNumber());
        entity.setName(addDTO.getName());
        entity.setStatus(addDTO.getStatus());
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
        entity.setInUser(userRepository.findByIdAndDeleteTime(currentUserID, null));
        entity.setFop(userRepository.findByIdAndDeleteTime(currentUserID, null));

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
        entity.setStatus(updateDTO.getStatus());
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
     * 只能查询自己的零件信息
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


    /**
     * 零件List界面的初始化数据
     * 在初始化的时候从数据库读取
     * 包括排放阶段、车型、动力总成以及分页的数据
     * 可以查询所有人的零件信息
     *
     * @return
     */
    @GetMapping("initAll")
    public TeilInitDataDTO initAll(TeilPageQueryDTO params) {
        TeilInitDataDTO teilInitDataDTO = new TeilInitDataDTO();
        //排放阶段、车型、动力总成
        teilInitDataDTO.setAbgasstufeList(abgasstufeRepository.findAll());
        teilInitDataDTO.setModellList(modellRepository.findAll());
        teilInitDataDTO.setAggregateList(aggregateRepository.findAll());
        //分页数据
        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO = pagedListAll(params);
        teilInitDataDTO.setList(pageResultDTO.getList());
        teilInitDataDTO.setPagination(pageResultDTO.getPagination());
        return teilInitDataDTO;
    }

}
