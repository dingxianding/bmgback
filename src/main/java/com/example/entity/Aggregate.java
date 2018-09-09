package com.example.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 动力总成
 */
@Entity
@Table(name = "tb_aggregate",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Aggregate implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id; //主键 自增

    @NotNull
    private String name;

    private String description;

    @ManyToMany(mappedBy = "aggregates", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Teil> teils;

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
}
