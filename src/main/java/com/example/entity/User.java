package com.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 人员
 */
@Entity
@Table(name = "tb_user",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"number"})})
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

    @NotNull
    private String password;

    @NotNull
    private int role;

    private int in_user;

    private Date in_time;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getIn_user() {
        return in_user;
    }

    public void setIn_user(int in_user) {
        this.in_user = in_user;
    }

    public Date getIn_time() {
        return in_time;
    }

    public void setIn_time(Date in_time) {
        this.in_time = in_time;
    }
}
