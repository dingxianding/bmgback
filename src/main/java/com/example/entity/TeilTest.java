package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 零件的实验信息
 */
@Entity
@Table(name = "tb_teil_test")
public class TeilTest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id; //主键 自增

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "teil_id", foreignKey = @ForeignKey(name = "fk_teil_test_teil"), nullable = false, unique = true)
    private Teil teil;

    //尺寸计划送样时间
    private Date sizeSollTime;
    //尺寸实际送样时间
    private Date sizeIstTime;
    //尺寸实验实际完成时间
    private Date sizeTestIstTime;
    //尺寸实验报告，文件
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "tb_teil_test_size_file", joinColumns = {
            @JoinColumn(name = "teil_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "file_id", referencedColumnName = "id")})
    private List<FileEntity> sizeTestReport = new ArrayList<FileEntity>();

    //材料计划送样时间
    private Date materialSollTime;
    //材料实际送样时间
    private Date materialIstTime;
    //材料实验实际完成时间
    private Date materialTestIstTime;
    //材料实验报告，文件
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "tb_teil_test_material_file", joinColumns = {
            @JoinColumn(name = "teil_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "file_id", referencedColumnName = "id")})
    private List<FileEntity> materialTestReport = new ArrayList<FileEntity>();

    //供应商自检实验完成时间
    private Date supplierTestIstTime;
    //供应商自检实验报告，文件
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "tb_teil_test_supplier_file", joinColumns = {
            @JoinColumn(name = "teil_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "file_id", referencedColumnName = "id")})
    private List<FileEntity> supplierTestReport = new ArrayList<FileEntity>();

    //零件寄WOB做性能时间
    private Date sendWobTime;
    //WOB实验完成时间
    private Date wobTestCompleteTime;
    //WOB实验报告，文件
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "tb_teil_test_wob_file", joinColumns = {
            @JoinColumn(name = "teil_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "file_id", referencedColumnName = "id")})
    private List<FileEntity> wobTestReport = new ArrayList<FileEntity>();

    //跑车耐久实验搭载车辆信息
    private String endureTestCarInfo;
    //备注
    private String remark;
    //是否沿用
    private Boolean ifCop;

    /**
     * 录入人员
     */
    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "in_user", foreignKey = @ForeignKey(name = "fk_teil_test_in_user"), nullable = false)
    private User inUser;

    /**
     * 录入时间
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date inTime;

    /**
     * 更新时间
     */
    @CreationTimestamp
    @Column(nullable = false)
    private Date updateTime;

    /**
     * 删除时间，删除操作并不真实删除数据
     */
    @JsonIgnore
    @Column(insertable = false)
    private Date deleteTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Teil getTeil() {
        return teil;
    }

    public void setTeil(Teil teil) {
        this.teil = teil;
    }

    public Date getSizeSollTime() {
        return sizeSollTime;
    }

    public void setSizeSollTime(Date sizeSollTime) {
        this.sizeSollTime = sizeSollTime;
    }

    public Date getSizeIstTime() {
        return sizeIstTime;
    }

    public void setSizeIstTime(Date sizeIstTime) {
        this.sizeIstTime = sizeIstTime;
    }

    public Date getSizeTestIstTime() {
        return sizeTestIstTime;
    }

    public void setSizeTestIstTime(Date sizeTestIstTime) {
        this.sizeTestIstTime = sizeTestIstTime;
    }

    public List<FileEntity> getSizeTestReport() {
        return sizeTestReport;
    }

    public void setSizeTestReport(List<FileEntity> sizeTestReport) {
        this.sizeTestReport = sizeTestReport;
    }

    public Date getMaterialSollTime() {
        return materialSollTime;
    }

    public void setMaterialSollTime(Date materialSollTime) {
        this.materialSollTime = materialSollTime;
    }

    public Date getMaterialIstTime() {
        return materialIstTime;
    }

    public void setMaterialIstTime(Date materialIstTime) {
        this.materialIstTime = materialIstTime;
    }

    public Date getMaterialTestIstTime() {
        return materialTestIstTime;
    }

    public void setMaterialTestIstTime(Date materialTestIstTime) {
        this.materialTestIstTime = materialTestIstTime;
    }

    public List<FileEntity> getMaterialTestReport() {
        return materialTestReport;
    }

    public void setMaterialTestReport(List<FileEntity> materialTestReport) {
        this.materialTestReport = materialTestReport;
    }

    public Date getSupplierTestIstTime() {
        return supplierTestIstTime;
    }

    public void setSupplierTestIstTime(Date supplierTestIstTime) {
        this.supplierTestIstTime = supplierTestIstTime;
    }

    public List<FileEntity> getSupplierTestReport() {
        return supplierTestReport;
    }

    public void setSupplierTestReport(List<FileEntity> supplierTestReport) {
        this.supplierTestReport = supplierTestReport;
    }

    public Date getSendWobTime() {
        return sendWobTime;
    }

    public void setSendWobTime(Date sendWobTime) {
        this.sendWobTime = sendWobTime;
    }

    public Date getWobTestCompleteTime() {
        return wobTestCompleteTime;
    }

    public void setWobTestCompleteTime(Date wobTestCompleteTime) {
        this.wobTestCompleteTime = wobTestCompleteTime;
    }

    public List<FileEntity> getWobTestReport() {
        return wobTestReport;
    }

    public void setWobTestReport(List<FileEntity> wobTestReport) {
        this.wobTestReport = wobTestReport;
    }

    public String getEndureTestCarInfo() {
        return endureTestCarInfo;
    }

    public void setEndureTestCarInfo(String endureTestCarInfo) {
        this.endureTestCarInfo = endureTestCarInfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getIfCop() {
        return ifCop;
    }

    public void setIfCop(Boolean ifCop) {
        this.ifCop = ifCop;
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
