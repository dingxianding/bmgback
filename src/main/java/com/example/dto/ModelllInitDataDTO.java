package com.example.dto;

import com.example.entity.Abgasstufe;
import com.example.entity.Aggregate;
import com.example.entity.Modell;
import com.example.entity.Platform;

import java.util.List;

/**
 * 零件List界面的初始化数据
 * 在初始化的时候从数据库读取
 */
public class ModelllInitDataDTO extends PageResultDTO {

    /**
     * 排放阶段
     */
    private List<Platform> platformList;

    /**
     * 动力总成
     */
    private List<Aggregate> aggregateList;

    public List<Platform> getPlatformList() {
        return platformList;
    }

    public void setPlatformList(List<Platform> platformList) {
        this.platformList = platformList;
    }

    public List<Aggregate> getAggregateList() {
        return aggregateList;
    }

    public void setAggregateList(List<Aggregate> aggregateList) {
        this.aggregateList = aggregateList;
    }
}
