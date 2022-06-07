package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Bbs;
import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.example.demo.mapper.MessageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageService extends ServiceImpl<MessageMapper, Message> {

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private UserService userService;

    public List<Message> findByForeign(Long foreignid) {
        LambdaQueryWrapper<Message> queryWrapper = Wrappers.<Message>lambdaQuery().eq(Message::getForeignId, foreignid);
        List<Message> list = list(queryWrapper);
        return list;
    }


    public List<Message> findByAid(Long aid) {
        LambdaQueryWrapper<Message> queryWrapper = Wrappers.<Message>lambdaQuery().eq(Message::getAid, aid);
        List<Message> list = list(queryWrapper);
        return list;
    }
}
