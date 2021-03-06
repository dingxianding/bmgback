package com.example.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *
 */
public class TeilPageQueryDTO extends PageQueryDTO {

    private String number;

    private String name;

    private Integer status;

    private String modell;//适用车型

    private String aggregate;//适用动力总成

    private String fop;//fop姓名

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getModell() {
        return modell;
    }

    public void setModell(String modell) {
        this.modell = modell;
    }

    public String getAggregate() {
        return aggregate;
    }

    public void setAggregate(String aggregate) {
        this.aggregate = aggregate;
    }

    public String getFop() {
        return fop;
    }

    public void setFop(String fop) {
        this.fop = fop;
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
