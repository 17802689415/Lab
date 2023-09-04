package com.it.pojo;

import java.util.List;

public class AllTask {
    private List<SampleInfo> sampleInfoList;
    private List<WaterCase> waterTestInfoList;

    public List<SampleInfo> getSampleInfoList() {
        return sampleInfoList;
    }

    public void setSampleInfoList(List<SampleInfo> sampleInfoList) {
        this.sampleInfoList = sampleInfoList;
    }

    public List<WaterCase> getWaterTestInfoList() {
        return waterTestInfoList;
    }

    public void setWaterTestInfoList(List<WaterCase> waterTestInfoList) {
        this.waterTestInfoList = waterTestInfoList;
    }
}
