package com.example.dto;

import com.example.entity.Abgasstufe;
import com.example.entity.Aggregate;
import com.example.entity.Modell;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 零件List界面的初始化数据
 * 在初始化的时候从数据库读取
 */
public class TeilInitDataDTO extends PageResultDTO {

    /**
     * 排放阶段
     */
    private List<Abgasstufe> abgasstufeList;

    /**
     * 车型
     */
    private List<Modell> modellList;

    /**
     * 动力总成
     */
    private List<Aggregate> aggregateList;

    public List<Abgasstufe> getAbgasstufeList() {
        return abgasstufeList;
    }

    public void setAbgasstufeList(List<Abgasstufe> abgasstufeList) {
        this.abgasstufeList = abgasstufeList;
    }

    public List<Modell> getModellList() {
        return modellList;
    }

    public void setModellList(List<Modell> modellList) {
        this.modellList = modellList;
    }

    public List<Aggregate> getAggregateList() {
        return aggregateList;
    }

    public void setAggregateList(List<Aggregate> aggregateList) {
        this.aggregateList = aggregateList;
    }
}
