package com.example.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 排放阶段
 */
@Entity
@Table(name = "tb_abgasstufe",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Abgasstufe implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id; //主键 自增

    @NotNull
    private String name;


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
}
