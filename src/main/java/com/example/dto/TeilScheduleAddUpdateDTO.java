package com.example.dto;

import java.util.Date;

/**
 *
 */
public class TeilScheduleAddUpdateDTO {
    private Integer id;
    private String teil;

    //询价资料时间
    private Date anfragedatenTime;
    //采购定点计划时间
    private Date cscSollTime;
    //采购定点实际时间
    private Date cscIstTime;
    //项目启动会计划时间
    private Date kickoffSollTime;
    //项目启动会实际时间
    private Date kickoffIstTime;
    //PAB编辑发送时间
    private Date pabEditSendTime;
    //PAB系统流转完成时间
    private Date pabFlowFinishTime;
    //PAB费用反馈的时间
    private Date pabCostFeedbackTime;
    //B-F计划时间
    private Date bfSollTime;
    //B-F实际时间
    private Date bfIstTime;
    //首模件实际时间
    private Date firstTryoutIstTime;
    //批量件计划时间
    private Date otsSollTime;
    //批量件实际时间
    private Date otsIstTime;
    //Aeko/AeA发生编号
    private String einsatzNum;
    //Aeko/AeA取消编号
    private String entfallNum;
    //BMG计划时间
    private Date bmgSollTime;
    //BMG实际时间
    private Date bmgIstTime;
    //BMG-EMP计划时间
    private Date bmgEmpSollTime;
    //BMG-EMP实际时间
    private Date bmgEmpIstTime;
    //BMG-ONLINE编号
    private String bmgOnlineNum;
    //FE54计划时间
    private Date fe54SollTime;
    //FE54实际时间
    private Date fe54IstTime;
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

    public Date getAnfragedatenTime() {
        return anfragedatenTime;
    }

    public void setAnfragedatenTime(Date anfragedatenTime) {
        this.anfragedatenTime = anfragedatenTime;
    }

    public Date getCscSollTime() {
        return cscSollTime;
    }

    public void setCscSollTime(Date cscSollTime) {
        this.cscSollTime = cscSollTime;
    }

    public Date getCscIstTime() {
        return cscIstTime;
    }

    public void setCscIstTime(Date cscIstTime) {
        this.cscIstTime = cscIstTime;
    }

    public Date getKickoffSollTime() {
        return kickoffSollTime;
    }

    public void setKickoffSollTime(Date kickoffSollTime) {
        this.kickoffSollTime = kickoffSollTime;
    }

    public Date getKickoffIstTime() {
        return kickoffIstTime;
    }

    public void setKickoffIstTime(Date kickoffIstTime) {
        this.kickoffIstTime = kickoffIstTime;
    }

    public Date getPabEditSendTime() {
        return pabEditSendTime;
    }

    public void setPabEditSendTime(Date pabEditSendTime) {
        this.pabEditSendTime = pabEditSendTime;
    }

    public Date getPabFlowFinishTime() {
        return pabFlowFinishTime;
    }

    public void setPabFlowFinishTime(Date pabFlowFinishTime) {
        this.pabFlowFinishTime = pabFlowFinishTime;
    }

    public Date getPabCostFeedbackTime() {
        return pabCostFeedbackTime;
    }

    public void setPabCostFeedbackTime(Date pabCostFeedbackTime) {
        this.pabCostFeedbackTime = pabCostFeedbackTime;
    }

    public Date getBfSollTime() {
        return bfSollTime;
    }

    public void setBfSollTime(Date bfSollTime) {
        this.bfSollTime = bfSollTime;
    }

    public Date getBfIstTime() {
        return bfIstTime;
    }

    public void setBfIstTime(Date bfIstTime) {
        this.bfIstTime = bfIstTime;
    }

    public Date getFirstTryoutIstTime() {
        return firstTryoutIstTime;
    }

    public void setFirstTryoutIstTime(Date firstTryoutIstTime) {
        this.firstTryoutIstTime = firstTryoutIstTime;
    }

    public Date getOtsSollTime() {
        return otsSollTime;
    }

    public void setOtsSollTime(Date otsSollTime) {
        this.otsSollTime = otsSollTime;
    }

    public Date getOtsIstTime() {
        return otsIstTime;
    }

    public void setOtsIstTime(Date otsIstTime) {
        this.otsIstTime = otsIstTime;
    }

    public String getEinsatzNum() {
        return einsatzNum;
    }

    public void setEinsatzNum(String einsatzNum) {
        this.einsatzNum = einsatzNum;
    }

    public String getEntfallNum() {
        return entfallNum;
    }

    public void setEntfallNum(String entfallNum) {
        this.entfallNum = entfallNum;
    }

    public Date getBmgSollTime() {
        return bmgSollTime;
    }

    public void setBmgSollTime(Date bmgSollTime) {
        this.bmgSollTime = bmgSollTime;
    }

    public Date getBmgIstTime() {
        return bmgIstTime;
    }

    public void setBmgIstTime(Date bmgIstTime) {
        this.bmgIstTime = bmgIstTime;
    }

    public Date getBmgEmpSollTime() {
        return bmgEmpSollTime;
    }

    public void setBmgEmpSollTime(Date bmgEmpSollTime) {
        this.bmgEmpSollTime = bmgEmpSollTime;
    }

    public Date getBmgEmpIstTime() {
        return bmgEmpIstTime;
    }

    public void setBmgEmpIstTime(Date bmgEmpIstTime) {
        this.bmgEmpIstTime = bmgEmpIstTime;
    }

    public String getBmgOnlineNum() {
        return bmgOnlineNum;
    }

    public void setBmgOnlineNum(String bmgOnlineNum) {
        this.bmgOnlineNum = bmgOnlineNum;
    }

    public Date getFe54SollTime() {
        return fe54SollTime;
    }

    public void setFe54SollTime(Date fe54SollTime) {
        this.fe54SollTime = fe54SollTime;
    }

    public Date getFe54IstTime() {
        return fe54IstTime;
    }

    public void setFe54IstTime(Date fe54IstTime) {
        this.fe54IstTime = fe54IstTime;
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
