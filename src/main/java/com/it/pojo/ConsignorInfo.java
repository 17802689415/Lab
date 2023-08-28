package com.it.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@TableName("consignor_info")
public class ConsignorInfo {
    @TableId
    private int id;

    private String caseNum;
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
}
