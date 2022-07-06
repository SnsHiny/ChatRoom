package com.SnHI.chat.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Pattern;

/**
 * 用户
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @TableId
    @Pattern(regexp = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u4e00-\\u9fa5]{2,5})", message = "用户名可以是2位以上的中文或6-16位的英文数字")
    private String uname;
    @Pattern(regexp = "^[0-9]{6,12}$", message = "密码由6-12位数字组成")
    private String password;
    private String uType;

}
