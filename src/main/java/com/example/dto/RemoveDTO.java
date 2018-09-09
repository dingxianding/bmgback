package com.example.dto;

import java.util.List;

/**
 * 删除DTO
 */
public class RemoveDTO {
    /**
     * 支持批量删除，id为1,2,3
     */
    private String id;

    private String method;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
