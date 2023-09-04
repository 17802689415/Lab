package com.it.pojo;

import org.apache.poi.ss.formula.functions.T;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;

public class CaseCommon {
    @Valid
    private ConsignorInfo consignorInfo;
    @Valid
    private SampleInfo sampleInfo;
    @Valid
    private List<SampleTestInfo> sampleTestInfo;
    @Valid
    private List<WaterTestInfo> waterTestInfo;
    @NotEmpty
    private String caseNum;
    private String sampleType;
    private int currentPage;
    private int pageSize;
    private String testType;

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getCaseNum() {
        return caseNum;
    }

    public void setCaseNum(String caseNum) {
        this.caseNum = caseNum;
    }

    public ConsignorInfo getConsignorInfo() {
        return consignorInfo;
    }

    public void setConsignorInfo(ConsignorInfo consignorInfo) {
        this.consignorInfo = consignorInfo;
    }

    public SampleInfo getSampleInfo() {
        return sampleInfo;
    }

    public void setSampleInfo(SampleInfo sampleInfo) {
        this.sampleInfo = sampleInfo;
    }

    public List<SampleTestInfo> getSampleTestInfo() {
        return sampleTestInfo;
    }

    public void setSampleTestInfo(List<SampleTestInfo> sampleTestInfo) {
        this.sampleTestInfo = sampleTestInfo;
    }

    public List<WaterTestInfo> getWaterTestInfo() {
        return waterTestInfo;
    }

    public void setWaterTestInfo(List<WaterTestInfo> waterTestInfo) {
        this.waterTestInfo = waterTestInfo;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }
    @Override
    public String toString() {
        return "CaseCommon{" +
                "consignorInfo=" + consignorInfo +
                ", sampleInfo=" + sampleInfo +
                ", sampleTestInfo=" + sampleTestInfo +
                '}';
    }
}
