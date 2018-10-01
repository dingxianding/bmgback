package com.example.dto;

import com.example.entity.Abgasstufe;
import com.example.entity.Aggregate;
import com.example.entity.Modell;
import com.example.entity.Teil;

import java.util.List;

/**
 * 零件List界面的初始化数据
 * 在初始化的时候从数据库读取
 */
public class TeilScheduleInitDataDTO extends PageResultDTO {
    private List<Teil> teilList;

    public List<Teil> getTeilList() {
        return teilList;
    }

    public void setTeilList(List<Teil> teilList) {
        this.teilList = teilList;
    }
}
