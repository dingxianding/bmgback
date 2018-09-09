package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 车型
 */
@Entity
@Table(name = "tb_modell",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Modell implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id; //主键 自增

    @NotNull
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "platform_id", foreignKey = @ForeignKey(name = "fk_modell_platform"))
    private Platform platform;

    //动力总成类型
//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinColumn(name = "aggregate_id", foreignKey = @ForeignKey(name = "fk_modell_aggregate"))
//    private Aggregate aggregate;

    //动力总成类型
    @JsonIgnoreProperties(value = {"teils"})
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "tb_modell_aggregate", joinColumns = {
            @JoinColumn(name = "modell_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "aggregate_id", referencedColumnName = "id")})
    private List<Aggregate> aggregates = new ArrayList<Aggregate>();

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

    //跑车计划
    private String runPlan;

    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "modells", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Teil> teils = new ArrayList<Teil>();

    /**
     * 录入人员
     */
    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "in_user", foreignKey = @ForeignKey(name = "fk_modell_in_user"))
    private User inUser;

    /**
     * 录入时间
     * 时间必须在程序里写，不能默认生成
     * 因为如果是非空的话就会因为是Null无法插入，如果可为空的话就会是null，时间只能自己写
     */
    @Column(nullable = false, columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP")
    private Date inTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除时间，删除操作并不真实删除数据
     */
    @JsonIgnore
    private Date deleteTime;

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

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public List<Aggregate> getAggregates() {
        return aggregates;
    }

    public void setAggregates(List<Aggregate> aggregates) {
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

    public String getRunPlan() {
        return runPlan;
    }

    public void setRunPlan(String runPlan) {
        this.runPlan = runPlan;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Teil> getTeils() {
        return teils;
    }

    public void setTeils(List<Teil> teils) {
        this.teils = teils;
    }

    public User getInUser() {
        return inUser;
    }

    public void setInUser(User inUser) {
        this.inUser = inUser;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }
}
