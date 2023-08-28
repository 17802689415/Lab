package com.it.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@TableName("sample_test_info")
public class SampleTestInfo {
    private int id;
    private String caseNum;
    @NotEmpty
    private String testItem;
    @NotEmpty
    private String batchNumber;
    @NotNull
    private double quantity;
    @NotEmpty
    private String specification;
    private String methodNumber;
    @NotNull
    private double acceptanceLimit;
    private String remark;
    private boolean urgent;
    private String createTime;
    private String inspector;
    private String status;
    @Version
    private int version;
    private String code;
    @TableField(exist = false)
    private ConsignorInfo consignorInfo;
    @TableField(exist = false)
    private SampleInfo sampleInfo;

}
