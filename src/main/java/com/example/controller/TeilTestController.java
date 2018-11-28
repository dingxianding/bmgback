package com.example.controller;

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

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 零件实验
 */
@RestController
@RequestMapping("myapi/teiltest")
public class TeilTestController {

    @Autowired
    private TeilTestRepository repository;

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

    @Autowired
    private FileRepository fileRepository;

    @GetMapping("")
    public List<TeilTest> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public TeilTest get(@PathVariable int id) {
        return repository.findByIdAndDeleteTime(id, null);
    }

    /**
     * 获取分页的数据，带查询条件、排序、分页
     *
     * @param params
     * @return
     */
    @GetMapping("pagedList")
    public PageResultDTO pagedList(TeilTestPageQueryDTO params) {
        int currentUserID = UserService.getCurrentUserID();
        int currentUserRole = UserService.getCurrentUserRole();

        // 动态查询条件
        Specification<TeilTest> spec = new Specification<TeilTest>() {
            @Override
            public Predicate toPredicate(Root<TeilTest> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<Predicate>();

                if (params.getNumber() != null) {
                    Join<Teil, TeilTest> join = root.join("teil", JoinType.LEFT);
                    predicate.add(cb.like(cb.lower(join.<String>get("number")), "%" + params.getNumber().toLowerCase() + "%"));
                    //predicate.add(cb.like(root.get("number"), "%" + params.getNumber() + "%"));
                }
                if (params.getName() != null) {
                    Join<Teil, TeilTest> join = root.join("teil", JoinType.LEFT);
                    predicate.add(cb.like(cb.lower(join.<String>get("name")), "%" + params.getName().toLowerCase() + "%"));
                    //predicate.add(cb.like(root.get("name"), "%" + params.getName() + "%"));
                }
                //只能查看自己的
                Join<User, TeilTest> join = root.join("inUser", JoinType.LEFT);
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
        Page<TeilTest> pageResult = repository.findAll(spec, pageable);

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
    public TeilTest save(@RequestBody TeilTestAddUpdateDTO addDTO) {
        //TODO 权限控制
        int currentUserID = UserService.getCurrentUserID();
        int currentUserRole = UserService.getCurrentUserRole();

        TeilTest entity = new TeilTest();
        entity.setTeil(teilRepository.findByNumberAndDeleteTime(addDTO.getTeil(), null));
        entity.setSizeSollTime(addDTO.getSizeSollTime());
        entity.setSizeIstTime(addDTO.getSizeIstTime());
        entity.setSizeTestIstTime(addDTO.getSizeTestIstTime());
        //尺寸实验报告
        List<FileEntity> sizeTestReport = new ArrayList();
        if (addDTO.getSizeTestReport() != null && addDTO.getSizeTestReport().size() > 0) {
            for (Integer i : addDTO.getSizeTestReport()) {
                sizeTestReport.add(fileRepository.findByIdAndDeleteTime(i, null));
            }
        }
        entity.setSizeTestReport(sizeTestReport);

        entity.setMaterialSollTime(addDTO.getMaterialSollTime());
        entity.setMaterialIstTime(addDTO.getMaterialIstTime());
        entity.setMaterialTestIstTime(addDTO.getMaterialTestIstTime());
        //材料实验报告
        List<FileEntity> materialTestReport = new ArrayList<FileEntity>();
        if (addDTO.getMaterialTestReport() != null && addDTO.getMaterialTestReport().size() > 0) {
            for (Integer i : addDTO.getMaterialTestReport()) {
                materialTestReport.add(fileRepository.findByIdAndDeleteTime(i, null));
            }
        }
        entity.setMaterialTestReport(materialTestReport);

        entity.setSupplierTestIstTime(addDTO.getSupplierTestIstTime());
        //供应商自检实验报告
        List<FileEntity> supplierTestReport = new ArrayList<FileEntity>();
        if (addDTO.getSupplierTestReport() != null && addDTO.getSupplierTestReport().size() > 0) {
            for (Integer i : addDTO.getSupplierTestReport()) {
                supplierTestReport.add(fileRepository.findByIdAndDeleteTime(i, null));
            }
        }
        entity.setSupplierTestReport(supplierTestReport);

        entity.setSendWobTime(addDTO.getSendWobTime());
        entity.setWobTestCompleteTime(addDTO.getWobTestCompleteTime());
        //WOB实验报告
        List<FileEntity> wobTestReport = new ArrayList<FileEntity>();
        if (addDTO.getWobTestReport() != null && addDTO.getWobTestReport().size() > 0) {
            for (Integer i : addDTO.getWobTestReport()) {
                wobTestReport.add(fileRepository.findByIdAndDeleteTime(i, null));
            }
        }
        entity.setWobTestReport(wobTestReport);

        entity.setEndureTestCarInfo(addDTO.getEndureTestCarInfo());
        entity.setRemark(addDTO.getRemark());
        entity.setIfCop(addDTO.getIfCop());

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
        TeilTest entity = repository.findByIdAndDeleteTime(id, null);
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
    public TeilTest update(@RequestBody TeilTestAddUpdateDTO updateDTO) {
        //TODO 权限控制

        //输入验证
        TeilTest entity = repository.findByIdAndDeleteTime(updateDTO.getId(), null);
        if (entity == null) {
            throw new BaseException("要更新的零件不存在");
        }

        entity.setTeil(teilRepository.findByNumberAndDeleteTime(updateDTO.getTeil(), null));
        entity.setSizeSollTime(updateDTO.getSizeSollTime());
        entity.setSizeIstTime(updateDTO.getSizeIstTime());
        entity.setSizeTestIstTime(updateDTO.getSizeTestIstTime());
        //尺寸实验报告
        List<FileEntity> sizeTestReport = new ArrayList<FileEntity>();
        if (updateDTO.getSizeTestReport() != null && updateDTO.getSizeTestReport().size() > 0) {
            for (Integer i : updateDTO.getSizeTestReport()) {
                sizeTestReport.add(fileRepository.findByIdAndDeleteTime(i, null));
            }
        }
        entity.setSizeTestReport(sizeTestReport);

        entity.setMaterialSollTime(updateDTO.getMaterialSollTime());
        entity.setMaterialIstTime(updateDTO.getMaterialIstTime());
        entity.setMaterialTestIstTime(updateDTO.getMaterialTestIstTime());
        //材料实验报告
        List<FileEntity> materialTestReport = new ArrayList<FileEntity>();
        if (updateDTO.getMaterialTestReport() != null && updateDTO.getMaterialTestReport().size() > 0) {
            for (Integer i : updateDTO.getMaterialTestReport()) {
                materialTestReport.add(fileRepository.findByIdAndDeleteTime(i, null));
            }
        }
        entity.setMaterialTestReport(materialTestReport);

        entity.setSupplierTestIstTime(updateDTO.getSupplierTestIstTime());
        //供应商自检实验报告
        List<FileEntity> supplierTestReport = new ArrayList<FileEntity>();
        if (updateDTO.getSupplierTestReport() != null && updateDTO.getSupplierTestReport().size() > 0) {
            for (Integer i : updateDTO.getSupplierTestReport()) {
                supplierTestReport.add(fileRepository.findByIdAndDeleteTime(i, null));
            }
        }
        entity.setSupplierTestReport(supplierTestReport);

        entity.setSendWobTime(updateDTO.getSendWobTime());
        entity.setWobTestCompleteTime(updateDTO.getWobTestCompleteTime());
        //WOB实验报告
        List<FileEntity> wobTestReport = new ArrayList<FileEntity>();
        if (updateDTO.getWobTestReport() != null && updateDTO.getWobTestReport().size() > 0) {
            for (Integer i : updateDTO.getWobTestReport()) {
                wobTestReport.add(fileRepository.findByIdAndDeleteTime(i, null));
            }
        }
        entity.setWobTestReport(wobTestReport);

        entity.setEndureTestCarInfo(updateDTO.getEndureTestCarInfo());
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
    public TeilTestInitDataDTO init(TeilTestPageQueryDTO params) {
        TeilTestInitDataDTO initDataDTO = new TeilTestInitDataDTO();
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
