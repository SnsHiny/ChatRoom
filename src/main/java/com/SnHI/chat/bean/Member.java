package com.SnHI.chat.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

/**
 * 群聊成员
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @TableId(type = IdType.AUTO)
    private Integer mid;
    //自定义校验规则
    @Pattern(regexp = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u4e00-\\u9fa5]{2,5})", message = "用户名可以是2位以上的中文或6-16位的英文数字")
    private String mName;
    private String mSex;
    private String birthday;
    private String address;
    @Pattern(regexp = "^1[0-9]{10}$", message = "手机号格式不正确")
    private String phoneNumber;
    @Pattern(regexp = "^([a-zA-Z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$", message = "邮箱格式不正确")
    private String Email;
    private String uname;
}
