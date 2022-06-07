package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user")
@Data
public class User {
    //Primary key -- automatically increasing
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    //Key -- username
    private String username;

    //Key -- password
    private String password;

    //Key -- nickname
    private String nickname;

    //Key -- age
    private Integer age;

    //Key -- sex
    private String sex;

    //Key -- avatar
    private String avatar;

    //Key -- role of user
    private Integer role;

    //Key -- state of user
    private Integer state;

    //Key -- emergency code of user
    private String emergency;

    //Key -- report of user
    private Integer report;

    //Key -- number of reports
    private Integer cnt;
}
