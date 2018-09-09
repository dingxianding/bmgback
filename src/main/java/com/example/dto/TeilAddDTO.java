package com.example.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class TeilAddDTO {

    /**
     * 零件号
     */
    private String number;

    /**
     * 零件名称
     */
    private String name;

    /**
     * 供应商
     */
    private String lieferant;

    /**
     * 供货状态
     */
    private String bezugsart;

    /**
     * 排放阶段
     */
    private String abgasstufe;

    /**
     * 最先投产车型
     */
    private String erstModell;

    /**
     * 最先投产车型动力总成
     */
    private String erstAggregate;

    /**
     * 一个零件可以适用于多个车型
     */
    private List<String> modells = new ArrayList<String>();


    /**
     * 一个零件可以属于多种动力总成
     */
    private List<String> aggregates = new ArrayList<String>();


    /**
     * 录入人员
     */
    private Integer inUser;

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

    public String getLieferant() {
        return lieferant;
    }

    public void setLieferant(String lieferant) {
        this.lieferant = lieferant;
    }

    public String getBezugsart() {
        return bezugsart;
    }

    public void setBezugsart(String bezugsart) {
        this.bezugsart = bezugsart;
    }

    public String getAbgasstufe() {
        return abgasstufe;
    }

    public void setAbgasstufe(String abgasstufe) {
        this.abgasstufe = abgasstufe;
    }

    public String getErstModell() {
        return erstModell;
    }

    public void setErstModell(String erstModell) {
        this.erstModell = erstModell;
    }

    public String getErstAggregate() {
        return erstAggregate;
    }

    public void setErstAggregate(String erstAggregate) {
        this.erstAggregate = erstAggregate;
    }

    public List<String> getModells() {
        return modells;
    }

    public void setModells(List<String> modells) {
        this.modells = modells;
    }

    public List<String> getAggregates() {
        return aggregates;
    }

    public void setAggregates(List<String> aggregates) {
        this.aggregates = aggregates;
    }

    public Integer getInUser() {
        return inUser;
    }

    public void setInUser(Integer inUser) {
        this.inUser = inUser;
    }
}
