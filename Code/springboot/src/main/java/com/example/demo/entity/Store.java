package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("store")
@Data
public class Store {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username;

    private String title;

    private Integer bbsId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

    private String content;
    private Integer follow;
    private Integer userId;
}
