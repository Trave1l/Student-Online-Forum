package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;


@Data
@TableName("message")
public class Message extends Model<Message> {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 内容
     */
    private String content;
    private String messageContent;//评论内容
    /**
     * 评论人
     */
    private String username;

    /**
     * 评论时间
     */
    private String time;

    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 关联id
     */
    private Long foreignId;

    /**
     * 评论人头像
     */
    private String avatar;

    /**
     * 父内容
     */
    private String parentMessage;

    private Integer aid;

    private String title;

    private String parentAuthor;

    private Integer follow;

    private String bbsAuthor;
}