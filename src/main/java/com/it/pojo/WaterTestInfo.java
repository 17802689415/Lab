package com.it.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@TableName("water_test_info")
public class WaterTestInfo {
    @TableId
    private int id;
    private String caseNum;
    private String sampleType;
    @NotEmpty
    private String waterNo;
    private String wiNo;
    @NotEmpty
    private String waterSendDate;
    @NotEmpty
    private String testItem;
    private boolean urgent;
    private String createTime;
    private String code;
    private String sampleTaker;
    private String receiveDate;
    private String rejectReason;
    private String inspector;
    private String status;
    @Version
    private int version;
    @TableField(exist = false)
    private ConsignorInfo consignorInfo;
}
