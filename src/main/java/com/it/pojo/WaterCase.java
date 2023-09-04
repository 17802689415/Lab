package com.it.pojo;

import java.util.List;

public class WaterCase {
    private String caseNum;
    private String createTime;
    private List<WaterTestInfo> list;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCaseNum() {
        return caseNum;
    }

    public void setCaseNum(String caseNum) {
        this.caseNum = caseNum;
    }

    public List<WaterTestInfo> getList() {
        return list;
    }

    public void setList(List<WaterTestInfo> list) {
        this.list = list;
    }
}
