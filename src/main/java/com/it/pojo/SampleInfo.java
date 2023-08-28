package com.it.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@TableName("sample_info")
public class SampleInfo {
    @TableId
    private int id;
    private String caseNum;
    @NotEmpty
    private String sampleName;
    @NotEmpty
    private String sampleType;
    @NotNull
    private double quantity;
    @NotEmpty
    private String batchNumber;
    @NotEmpty
    private String testPurpose;
    @NotEmpty
    private String sampleSendDate;
    @NotEmpty
    private String sampleDisposal;
    @NotEmpty
    private String sampleStorageCondition;
    private String createTime;
    private String sampleTaker;
    private String receiveDate;
    @Version
    private int version;
    private String rejectReason;
    private String status;
    @TableField(exist = false)
    private List<SampleTestInfo> sampleTestInfoList;
    @TableField(exist = false)
    private String testType;
}
