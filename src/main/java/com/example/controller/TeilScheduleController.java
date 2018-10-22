package com.example.controller;

import com.example.dto.*;
import com.example.entity.Aggregate;
import com.example.entity.Modell;
import com.example.entity.Teil;
import com.example.entity.TeilSchedule;
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

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 零件进度
 */
@RestController
@RequestMapping("myapi/teilschedule")
public class TeilScheduleController {

    @Autowired
    private TeilScheduleRepository repository;

    @Autowired
    private TeilRepository teilRepository;

    @Autowired
    private AbgasstufeRepository abgasstufeRepository;

    @Autowired
    private ModellRepository modellRepository;

    @Autowired
    private AggregateRepository aggregateRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public List<TeilSchedule> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public TeilSchedule get(@PathVariable int id) {
        return repository.findByIdAndDeleteTime(id, null);
    }

    /**
     * 获取分页的数据，带查询条件、排序、分页
     *
     * @param params
     * @return
     */
    @GetMapping("pagedList")
    public PageResultDTO pagedList(TeilSchedulePageQueryDTO params) {

        // 动态查询条件
        Specification<TeilSchedule> spec = (root, query, cb) -> {
            List<Predicate> predicate = new ArrayList<>();

            if (params.getNumber() != null) {
                Join<Teil, TeilSchedule> join = root.join("teil", JoinType.LEFT);
                predicate.add(cb.like(cb.lower(join.get("number")), "%" + params.getNumber().toLowerCase() + "%"));
                //predicate.add(cb.like(root.get("number"), "%" + params.getNumber() + "%"));
            }
            if (params.getName() != null) {
                Join<Teil, TeilSchedule> join = root.join("teil", JoinType.LEFT);
                predicate.add(cb.like(cb.lower(join.get("name")), "%" + params.getName().toLowerCase() + "%"));
                //predicate.add(cb.like(root.get("name"), "%" + params.getName() + "%"));
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
        Page<TeilSchedule> pageResult = repository.findAll(spec, pageable);

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
    public TeilSchedule save(@RequestBody TeilScheduleAddUpdateDTO addDTO) {
        //TODO 权限控制
        int currentUserID = UserService.getCurrentUserID();
        int currentUserRole = UserService.getCurrentUserRole();

        TeilSchedule entity = new TeilSchedule();
        entity.setTeil(teilRepository.findByNumberAndDeleteTime(addDTO.getTeil(), null));
        entity.setAnfragedatenTime(addDTO.getAnfragedatenTime());
        entity.setCscSollTime(addDTO.getCscSollTime());
        entity.setCscIstTime(addDTO.getCscIstTime());
        entity.setKickoffSollTime(addDTO.getKickoffSollTime());
        entity.setKickoffIstTime(addDTO.getKickoffIstTime());
        entity.setPabEditSendTime(addDTO.getPabEditSendTime());
        entity.setPabFlowFinishTime(addDTO.getPabFlowFinishTime());
        entity.setPabCostFeedbackTime(addDTO.getPabCostFeedbackTime());
        entity.setBfSollTime(addDTO.getBfSollTime());
        entity.setBfIstTime(addDTO.getBfIstTime());
        entity.setFirstTryoutIstTime(addDTO.getFirstTryoutIstTime());
        entity.setOtsSollTime(addDTO.getOtsSollTime());
        entity.setOtsIstTime(addDTO.getOtsIstTime());
        entity.setEinsatzNum(addDTO.getEinsatzNum());
        entity.setEntfallNum(addDTO.getEntfallNum());
        entity.setBmgSollTime(addDTO.getBmgSollTime());
        entity.setBmgIstTime(addDTO.getBmgIstTime());
        entity.setBmgEmpSollTime(addDTO.getBmgEmpSollTime());
        entity.setBmgEmpIstTime(addDTO.getBmgEmpIstTime());
        entity.setBmgOnlineNum(addDTO.getBmgOnlineNum());
        entity.setFe54SollTime(addDTO.getFe54SollTime());
        entity.setFe54IstTime(addDTO.getFe54IstTime());
        entity.setRemark(addDTO.getRemark());
        entity.setIfCop(addDTO.getIfCop());

        entity.setInUser(userRepository.findByIdAndDeleteTime(currentUserID, null));
//        entity.setInTime(new Date());
//        entity.setUpdateTime(new Date());
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
        TeilSchedule entity = repository.findByIdAndDeleteTime(id, null);
        if (entity == null) {
            throw new BaseException("要删除的零件进度不存在");
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
    public TeilSchedule update(@RequestBody TeilScheduleAddUpdateDTO updateDTO) {
        //TODO 权限控制

        //输入验证
        TeilSchedule entity = repository.findByIdAndDeleteTime(updateDTO.getId(), null);
        if (entity == null) {
            throw new BaseException("要更新的零件不存在");
        }

        entity.setTeil(teilRepository.findByNumberAndDeleteTime(updateDTO.getTeil(), null));
        entity.setAnfragedatenTime(updateDTO.getAnfragedatenTime());
        entity.setCscSollTime(updateDTO.getCscSollTime());
        entity.setCscIstTime(updateDTO.getCscIstTime());
        entity.setKickoffSollTime(updateDTO.getKickoffSollTime());
        entity.setKickoffIstTime(updateDTO.getKickoffIstTime());
        entity.setPabEditSendTime(updateDTO.getPabEditSendTime());
        entity.setPabFlowFinishTime(updateDTO.getPabFlowFinishTime());
        entity.setPabCostFeedbackTime(updateDTO.getPabCostFeedbackTime());
        entity.setBfSollTime(updateDTO.getBfSollTime());
        entity.setBfIstTime(updateDTO.getBfIstTime());
        entity.setFirstTryoutIstTime(updateDTO.getFirstTryoutIstTime());
        entity.setOtsSollTime(updateDTO.getOtsSollTime());
        entity.setOtsIstTime(updateDTO.getOtsIstTime());
        entity.setEinsatzNum(updateDTO.getEinsatzNum());
        entity.setEntfallNum(updateDTO.getEntfallNum());
        entity.setBmgSollTime(updateDTO.getBmgSollTime());
        entity.setBmgIstTime(updateDTO.getBmgIstTime());
        entity.setBmgEmpSollTime(updateDTO.getBmgEmpSollTime());
        entity.setBmgEmpIstTime(updateDTO.getBmgEmpIstTime());
        entity.setBmgOnlineNum(updateDTO.getBmgOnlineNum());
        entity.setFe54SollTime(updateDTO.getFe54SollTime());
        entity.setFe54IstTime(updateDTO.getFe54IstTime());
        entity.setRemark(updateDTO.getRemark());
        entity.setIfCop(updateDTO.getIfCop());

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
    public TeilScheduleInitDataDTO init(TeilSchedulePageQueryDTO params) {
        TeilScheduleInitDataDTO initDataDTO = new TeilScheduleInitDataDTO();
        //零件
        //TODO 应该只能获取自己的，未添加过信息的
        initDataDTO.setTeilList(teilRepository.findAll());
        //分页数据
        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO = pagedList(params);
        initDataDTO.setList(pageResultDTO.getList());
        initDataDTO.setPagination(pageResultDTO.getPagination());
        return initDataDTO;
    }


}
