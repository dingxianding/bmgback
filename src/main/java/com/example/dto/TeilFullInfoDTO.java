package com.example.dto;

import com.example.entity.Teil;
import com.example.entity.TeilSchedule;
import com.example.entity.TeilTest;

/**
 * 零件所有信息，包括基础信息、进度信息、实验信息
 */
public class TeilFullInfoDTO {
    private Teil teil;
    private TeilSchedule teilSchedule;
    private TeilTest teilTest;

    public Teil getTeil() {
        return teil;
    }

    public void setTeil(Teil teil) {
        this.teil = teil;
    }

    public TeilSchedule getTeilSchedule() {
        return teilSchedule;
    }

    public void setTeilSchedule(TeilSchedule teilSchedule) {
        this.teilSchedule = teilSchedule;
    }

    public TeilTest getTeilTest() {
        return teilTest;
    }

    public void setTeilTest(TeilTest teilTest) {
        this.teilTest = teilTest;
    }
}
