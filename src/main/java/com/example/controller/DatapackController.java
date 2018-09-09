package com.example.controller;

import com.example.dto.*;
import com.example.entity.Datapack;
import com.example.repository.DatapackRepository;
import com.example.service.DatapackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据包控制类
 */
@RestController
@RequestMapping("datapack")
public class DatapackController {

    @Autowired
    private DatapackRepository repository;

    @Autowired
    private DatapackService dataPackService;

    @GetMapping("get/{id}")
    public Datapack get(@PathVariable int id) {
        return repository.findOne(id);
    }

    @PostMapping("save")
    public ExecuteDTO save(@RequestBody Datapack entity) {
//        if (entity.date == null) {
//            entity.date = new Date();
//        }
        repository.save(entity);
        return new ExecuteDTO(true, "保存成功", entity.getId());
    }

    @PostMapping("loadPage")
    public PageResultDTO loadPage(@RequestBody PageQueryParamDTO params) {

//        // 动态查询条件
//        Specification<Datapack> spec = (root, query, cb) -> {
//            if (params.getQuery() != null) {
//                // 筛选 会员姓名
//                query.where(cb.equal(root.get("name"), params.getQuery().toString()));
//            }
//
//            return null;
//        };
//        Pageable pageable = new PageRequest(params.getCurrent() - 1, params.getPageSize());
//        Page<Datapack> pageResult = repository.findAll(spec, pageable);
//
//        // 返回分页数据
//        return new PageResultDTO(pageResult.getTotalElements(), pageResult.getContent());
        return null;
    }

    @GetMapping("remove/{id}")
    public ExecuteDTO remove(@PathVariable int id) {
        repository.delete(id);
        return new ExecuteDTO(true, "删除成功", id);
    }

    /**
     * 获取根据level获取数据包，一般用于绑定level=0的，即数据包类型，可用于导航栏绑定
     *
     * @param id
     * @return
     */
    @GetMapping("level/{id}")
    public List<Datapack> getPackByLevel(@PathVariable int id) {
        return repository.findByLevel(id);
    }

    /**
     * 根据数据包ID获取数据包的属性，常用于绑定列表表头
     *
     * @param id
     * @return
     */
//    @GetMapping("attr/{id}")
//    public List<Datapack> getAttrByPackId(@PathVariable int id) {
//        return repository.findAttrByPackId(id);
//    }

    /**
     * 根据数据包ID获取数据包的属性，常用于绑定列表表头
     *
     * @param id
     * @return
     */
    @GetMapping("header/{id}")
    public List<Header> getAttrByPackId(@PathVariable int id) {
        List<Header> headerList = new ArrayList<>();
        List<Datapack> datapackList = repository.findAttrByPackId(id);
        for (int i = 0; i < datapackList.size(); i++) {
            Header header = new Header();
            Datapack datapack = datapackList.get(i);
            header.setDataIndex(datapack.getName());
            header.setTitle(datapack.getDescription());
            header.setAlign("center");
            //固定入库时间列，并放到第一列
            if (datapack.getName().equals("in_time")) {
                header.setFixed("left");
                headerList.add(0, header);
            } else {
                headerList.add(header);
            }
        }
        return headerList;
    }

    //获取表头
//    <select id="queryDataPackAttrByPackId" resultType="com.i51zhishi.web.vo.DataPackAttrVO">
//    SELECT id, parent_id, level, t.name, t.desc
//    FROM okw_datapack t
//    WHERE level=2 and parent_id in (select id from okw_datapack where parent_id=#{dataPackId})
//    ORDER BY id ASC
//    </select>

    //    queryCount   "select count(0) count from " + MysqlConfig.mDBPrefix + filterVO.getDataName() + " where 1=1 ";
//            queryLikeData    "select * from " + MysqlConfig.mDBPrefix + filterVO.getDataName() + " where 1=1 ";

    /**
     * 返回包括数据头的分页的数据，包括数据头、数据内容、分页信息
     * localhost:18080/datapack/pageHeaderData?current=1&pageSize=10&dataId=1&dataName=telemetry_attitude
     *
     * @param params
     * @return
     */
    @GetMapping("pageHeaderData")
    public PageHeaderResultDTO pageHeaderData(PageQueryDatapackDTO params) {
        PageHeaderResultDTO pageHeaderResultDTO = new PageHeaderResultDTO();

        pageHeaderResultDTO.setHeader(repository.findAttrByPackId(params.getDataId()));

        Pagination pagination = new Pagination();
        pagination.setCurrent(params.getCurrentPage());
        pagination.setPageSize(params.getPageSize());
        pagination.setTotal(dataPackService.countData(params));

        pageHeaderResultDTO.setPagination(pagination);
        pageHeaderResultDTO.setList(dataPackService.findData(params));

        return pageHeaderResultDTO;
    }

    /**
     * 返回分页的数据，包括数据内容、分页信息
     * localhost:18080/datapack/pageData?current=1&pageSize=10&dataId=1&dataName=telemetry_attitude
     *
     * @param params
     * @return
     */
    @GetMapping("pageData")
    public PageResultDTO pageData(PageQueryDatapackDTO params) {
        PageResultDTO pageResultDTO = new PageResultDTO();

        Pagination pagination = new Pagination();
        pagination.setCurrent(params.getCurrentPage());
        pagination.setPageSize(params.getPageSize());
        pagination.setTotal(dataPackService.countData(params));

        pageResultDTO.setPagination(pagination);
        pageResultDTO.setList(dataPackService.findData(params));

        return pageResultDTO;
    }


    /**
     * 获取指定列的数据，不分页
     * 一般用于图表显示时，只获取选中列的数据
     *
     * @param params
     * @return
     */
    @GetMapping("columnData")
    public List<Map<String, Object>> columnData(PageQueryDatapackDTO params) {
        return dataPackService.findColumnData(params);
    }
}
