package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Message;
import org.apache.ibatis.annotations.Select;

public interface MessageMapper extends BaseMapper<Message> {


    Message selectOne(Message m);
}
