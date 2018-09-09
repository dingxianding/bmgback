package com.example.dto;

import java.util.List;

/**
 * 分页结果DTO
 *
 * @author 刘冬 博客出处：http://www.cnblogs.com/GoodHelper/
 */
public class PageResultDTO {
    private List<?> list;

    private Pagination pagination;

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
