package com.example.dto;

import com.example.entity.Datapack;

import java.util.List;

/**
 * 分页结果DTO
 *
 * @author 刘冬 博客出处：http://www.cnblogs.com/GoodHelper/
 */
public class PageHeaderResultDTO {
    private List<Datapack> header;

    private List<?> list;

    private Pagination pagination;

    public List<Datapack> getHeader() {
        return header;
    }

    public void setHeader(List<Datapack> header) {
        this.header = header;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
