package com.it.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@TableName("consignor_info")
public class ConsignorInfo {
    @TableId
    private int id;

    private String caseNum;
    @NotEmpty
    private String empId;
    @NotEmpty
    private String name;
    @NotEmpty
    private String mobile;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String department;
    @NotEmpty
    private String workCell;
    @NotEmpty
    @Email
    private String notifier;
    private String applyDate;
    @TableField(exist = false)
    private SampleInfo sampleInfo;
    @TableField(exist = false)
    private List<SampleTestInfo> sampleTestInfoList;
    @TableField(exist = false)
    private List<WaterTestInfo> waterTestInfo;
    @TableField(exist = false)
    private int currentPage;
    @TableField(exist = false)
    private int pageSize;
    @TableField(exist = false)
    private String testType;

}
