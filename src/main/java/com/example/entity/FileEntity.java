package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文件
 */
@Entity
@Table(name = "tb_file",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"path"})})
public class FileEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id; //主键 自增

    //文件名
    @NotNull
    private String name;

    //文件路径
    @NotNull
    private String path;

    //文件大小
    @NotNull
    private Long size;

    /**
     * 录入人员
     */
    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "in_user", foreignKey = @ForeignKey(name = "fk_file_in_user"))
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
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
