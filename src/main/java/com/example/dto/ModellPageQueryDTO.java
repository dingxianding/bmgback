package com.example.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 分页查询参数DTO
 */
public class ModellPageQueryDTO extends PageQueryDTO {


    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fromTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date toTime;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getFromTime() {
        return fromTime;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public Date getToTime() {
        return toTime;
    }

    public void setToTime(Date toTime) {
        this.toTime = toTime;
    }

}
