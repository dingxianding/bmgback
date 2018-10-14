package com.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 人员
 */
@Entity
@Table(name = "tb_user",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"number"}), @UniqueConstraint(columnNames = {"username"})})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id; //主键 自增

    /**
     * 工号
     */
    @NotNull
    private String number;

    /**
     * 姓名
     */
    @NotNull
    private String name;

    //登录用户名
    @Column(nullable = false, unique = true)
    private String username;

    @NotNull
    private String password;

    @NotNull
    private Integer role;

    /**
     * 备注
     */
    private String remark;


    /**
     * 录入人员
     */
    //@JsonIgnoreProperties(value = {"inUser"})
    @JsonIgnore
    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "in_user", foreignKey = @ForeignKey(name = "fk_user_in_user"))
    private User inUser;

    /**
     * 录入时间
     * 时间必须在程序里写，不能默认生成（用了CreationTimestamp，就不需要程序里写了）
     * 因为如果是非空的话就会因为是Null无法插入，如果可为空的话就会是null，时间只能自己写
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
