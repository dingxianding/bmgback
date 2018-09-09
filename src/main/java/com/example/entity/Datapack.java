package com.example.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 数据包类型
 * "CREATE TABLE `" + Config.mDBPrefix + "datapack`(" +
 * "`id` int(10) unsigned NOT NULL AUTO_INCREMENT, PRIMARY KEY(`id`), " +
 * "`parent_id` smallint(5) unsigned NOT NULL DEFAULT '0', KEY `parent_id` (`parent_id`), " +
 * "`level` tinyint(1) NOT NULL DEFAULT '2', KEY `level` (`level`), " +
 * "`name` varchar(64) NOT NULL, " +
 * "`description` varchar(128) NOT NULL); ";
 */
@Entity
@Table(name = "okw_datapack")
public class Datapack implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id; //主键 自增

    @NotNull
    private Integer parentId;

    @NotNull
    private Integer level;

    @NotNull
    private String name;

    @NotNull
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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
}
