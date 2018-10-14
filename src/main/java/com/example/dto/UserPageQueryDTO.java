package com.example.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *
 */
public class UserPageQueryDTO extends PageQueryDTO {

    private String number;

    private String name;

    private Integer role;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fromTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date toTime;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
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
