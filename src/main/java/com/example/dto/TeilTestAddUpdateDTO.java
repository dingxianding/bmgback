package com.example.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class TeilTestAddUpdateDTO {
    private Integer id;
    private String teil;

    //尺寸计划送样时间
    private Date sizeSollTime;
    //尺寸实际送样时间
    private Date sizeIstTime;
    //尺寸实验实际完成时间
    private Date sizeTestIstTime;
    //尺寸实验报告，文件
    private List<Integer> sizeTestReport = new ArrayList<Integer>();

    //材料计划送样时间
    private Date materialSollTime;
    //材料实际送样时间
    private Date materialIstTime;
    //材料实验实际完成时间
    private Date materialTestIstTime;
    //材料实验报告，文件
    private List<Integer> materialTestReport = new ArrayList<Integer>();

    //供应商自检实验完成时间
    private Date supplierTestIstTime;
    //供应商自检实验报告，文件
    private List<Integer> supplierTestReport = new ArrayList<Integer>();

    //零件寄WOB做性能时间
    private Date sendWobTime;
    //WOB实验完成时间
    private Date wobTestCompleteTime;
    //WOB实验报告，文件
    private List<Integer> wobTestReport = new ArrayList<Integer>();

    //跑车耐久实验搭载车辆信息
    private String endureTestCarInfo;
    //备注
    private String remark;
    //是否沿用
    private Boolean ifCop;

    /**
     * 录入人员
     */
    private Integer inUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeil() {
        return teil;
    }

    public void setTeil(String teil) {
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

    public List<Integer> getSizeTestReport() {
        return sizeTestReport;
    }

    public void setSizeTestReport(List<Integer> sizeTestReport) {
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

    public List<Integer> getMaterialTestReport() {
        return materialTestReport;
    }

    public void setMaterialTestReport(List<Integer> materialTestReport) {
        this.materialTestReport = materialTestReport;
    }

    public Date getSupplierTestIstTime() {
        return supplierTestIstTime;
    }

    public void setSupplierTestIstTime(Date supplierTestIstTime) {
        this.supplierTestIstTime = supplierTestIstTime;
    }

    public List<Integer> getSupplierTestReport() {
        return supplierTestReport;
    }

    public void setSupplierTestReport(List<Integer> supplierTestReport) {
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

    public List<Integer> getWobTestReport() {
        return wobTestReport;
    }

    public void setWobTestReport(List<Integer> wobTestReport) {
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

    public Integer getInUser() {
        return inUser;
    }

    public void setInUser(Integer inUser) {
        this.inUser = inUser;
    }
}
