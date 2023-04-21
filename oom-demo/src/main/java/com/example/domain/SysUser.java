package com.example.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author yfc
 * @date 2023/4/13 9:43
 */
@Data
@TableName("t_sys_user")
public class SysUser {

    private Long id;

    private String name;
    private Integer age;
    private String address;
    private String remark;
    private String nickName;
    private String email;
    private String phone;

    private Date insertTime;
    private Date lastModifyTime;

}
