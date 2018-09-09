package com.example.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 分页查询参数DTO
 */
public class PageQueryDTO {

    /**
     * 页码
     */
    private int currentPage;

    /**
     * 每页数量
     */
    private int pageSize;

    /**
     * 排序
     * sorter=number_descend或number_ascend
     */
    private String sorter;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSorter() {
        return sorter;
    }

    public void setSorter(String sorter) {
        this.sorter = sorter;
    }
}
