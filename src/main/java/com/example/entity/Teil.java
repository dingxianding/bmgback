package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 零件
 * 一个零件只有一个供应商，一个零件可以适用于多个车型，一个零件可以属于多种动力总成。
 * 一个零件只属于一个最先投产车型、一个最先投产车型动力总成。
 * 多对多参考 https://blog.csdn.net/lewis_007/article/details/53006602
 */
@Entity
@Table(name = "tb_teil",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"number"})})
public class Teil implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号，主键，自增
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 零件号
     */
    @NotNull
    private String number;

    /**
     * 零件名称
     */
    @NotNull
    private String name;

    /**
     * 供应商
     */
    private String lieferant;

    /**
     * 供货状态
     */
    private String bezugsart;

    /**
     * 排放阶段
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "abgasstufe_id", foreignKey = @ForeignKey(name = "fk_teil_abgasstufe"))
    private Abgasstufe abgasstufe;

    /**
     * 最先投产车型
     * 用JsonIgnoreProperties，避免死循环，参考https://www.imooc.com/wenda/detail/381073
     */
    @JsonIgnoreProperties(value = {"teils"})
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "erst_modell_id", foreignKey = @ForeignKey(name = "fk_teil_erst_modell"))
    private Modell erstModell;

    /**
     * 最先投产车型动力总成
     */
    @JsonIgnoreProperties(value = {"teils"})
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "erst_aggregate_id", foreignKey = @ForeignKey(name = "fk_teil_erst_aggregate"))
    private Aggregate erstAggregate;

    /**
     * 一个零件可以适用于多个车型
     * TODO 外键无法取名
     */
    @JsonIgnoreProperties(value = {"teils"})
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "tb_teil_modell", joinColumns = {
            @JoinColumn(name = "teil_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "modell_id", referencedColumnName = "id")})
    private List<Modell> modells = new ArrayList<Modell>();


    /**
     * 一个零件可以属于多种动力总成
     * TODO 外键无法取名
     */
    @JsonIgnoreProperties(value = {"teils"})
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "tb_teil_aggregate", joinColumns = {
            @JoinColumn(name = "teil_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "aggregate_id", referencedColumnName = "id")})
    private List<Aggregate> aggregates = new ArrayList<Aggregate>();

    /**
     * 当前零件主管工程师，一般与录入人员相同
     */
    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "fop_id", foreignKey = @ForeignKey(name = "fk_teil_fop"))
    private User fop;

    /**
     * 录入人员
     */
    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "in_user", foreignKey = @ForeignKey(name = "fk_teil_in_user"))
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
    @Column(nullable = false, columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP")
    private Date updateTime;

    /**
     * 删除时间，删除操作并不真实删除数据
     */
    private Date deleteTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getLieferant() {
        return lieferant;
    }

    public void setLieferant(String lieferant) {
        this.lieferant = lieferant;
    }

    public String getBezugsart() {
        return bezugsart;
    }

    public void setBezugsart(String bezugsart) {
        this.bezugsart = bezugsart;
    }

    public Abgasstufe getAbgasstufe() {
        return abgasstufe;
    }

    public void setAbgasstufe(Abgasstufe abgasstufe) {
        this.abgasstufe = abgasstufe;
    }

    public Modell getErstModell() {
        return erstModell;
    }

    public void setErstModell(Modell erstModell) {
        this.erstModell = erstModell;
    }

    public Aggregate getErstAggregate() {
        return erstAggregate;
    }

    public void setErstAggregate(Aggregate erstAggregate) {
        this.erstAggregate = erstAggregate;
    }

    public List<Modell> getModells() {
        return modells;
    }

    public void setModells(List<Modell> modells) {
        this.modells = modells;
    }

    public List<Aggregate> getAggregates() {
        return aggregates;
    }

    public void setAggregates(List<Aggregate> aggregates) {
        this.aggregates = aggregates;
    }

    public User getFop() {
        return fop;
    }

    public void setFop(User fop) {
        this.fop = fop;
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
