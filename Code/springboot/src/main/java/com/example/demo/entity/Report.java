package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;

@TableName("report")
@Data
public class Report {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String portName;

    private String reContent;

    private Integer reMid;

    private Integer portId;

    private String message;

    private String reTitle;

    private Integer reTid;

    private String reUname;

    private Integer reUid;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

}