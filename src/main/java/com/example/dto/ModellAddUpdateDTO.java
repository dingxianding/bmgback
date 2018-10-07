package com.example.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class ModellAddUpdateDTO {
    private Integer id; //主键 自增

    private String name;

    private String platform;

    /**
     * 多种动力总成
     */
    private List<String> aggregates = new ArrayList<String>();

    //VFF时间
    private Date vffTime;

    //PVS时间
    private Date pvsTime;

    //0S TBT时间
    private Date osTbtTime;

    //0S时间
    private Date osTime;

    //SOP TBT时间
    private Date sopTbtTime;

    //SOP时间
    private Date sopTime;

    //跑车数量（SWP/SVP/SPH/4KZ）
    private Integer runCount;

    private String description;

    //跑车计划
    private List<Integer> runPlan = new ArrayList<Integer>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public List<String> getAggregates() {
        return aggregates;
    }

    public void setAggregates(List<String> aggregates) {
        this.aggregates = aggregates;
    }

    public Date getVffTime() {
        return vffTime;
    }

    public void setVffTime(Date vffTime) {
        this.vffTime = vffTime;
    }

    public Date getPvsTime() {
        return pvsTime;
    }

    public void setPvsTime(Date pvsTime) {
        this.pvsTime = pvsTime;
    }

    public Date getOsTbtTime() {
        return osTbtTime;
    }

    public void setOsTbtTime(Date osTbtTime) {
        this.osTbtTime = osTbtTime;
    }

    public Date getOsTime() {
        return osTime;
    }

    public void setOsTime(Date osTime) {
        this.osTime = osTime;
    }

    public Date getSopTbtTime() {
        return sopTbtTime;
    }

    public void setSopTbtTime(Date sopTbtTime) {
        this.sopTbtTime = sopTbtTime;
    }

    public Date getSopTime() {
        return sopTime;
    }

    public void setSopTime(Date sopTime) {
        this.sopTime = sopTime;
    }

    public Integer getRunCount() {
        return runCount;
    }

    public void setRunCount(Integer runCount) {
        this.runCount = runCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getRunPlan() {
        return runPlan;
    }

    public void setRunPlan(List<Integer> runPlan) {
        this.runPlan = runPlan;
    }
}
