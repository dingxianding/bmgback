package com.example.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 零件的进度信息
 */
@Entity
@Table(name = "tb_teil_scheduel")
public class TeilSchedule implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id; //主键 自增

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "teil_id", foreignKey = @ForeignKey(name = "fk_teil_scheduel_teil"))
    private Teil teil;

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
    private Boolean isCop;

    /**
     * 录入人员
     */
    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "in_user", foreignKey = @ForeignKey(name = "fk_teil_scheduel_in_user"))
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


}
