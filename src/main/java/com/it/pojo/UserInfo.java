package com.it.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@TableName("user_info")
public class UserInfo implements Serializable {
    @TableId
    private int id;
    @NotEmpty
    private String empId;
    @NotEmpty
    private String workCell;
    private String chineseName;
    private String mobile;
    @NotEmpty
    @Email
    private String email;
    @NotNull
    private String role;
    private String backUp;
    private Boolean active;
}
