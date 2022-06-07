package com.example.demo.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.example.demo.mapper.MessageMapper;
import com.example.demo.service.MessageService;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.*;
import com.auth0.jwt.JWT;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    MessageMapper messageMapper;
    @Resource
    private MessageService messageService;
    
    @PostMapping
    public Result<?> save(@RequestBody Message message) {
        message.setTime(DateUtil.formatDateTime(new Date()));
        return Result.success(messageService.save(message));
    }

    @PutMapping
    public Result<?> update(@RequestBody Message message) {
        int fo = message.getFollow();
        fo++;
        message.setFollow(fo);
        messageMapper.updateById(message);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        messageMapper.deleteById(id);
        return Result.success();
    }

    /*
     * Path Variable: ID
     * Output: All messages with same ID
     * */
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(messageMapper.selectById(id));
    }

    /*
    * Path Variable: ForeignID
    * Output: All messages with same ForeignID
    * */
    @GetMapping("/foreign/{foreignid}")
    public Result<?> getForeignId(@PathVariable Long foreignid) {
        Message m = new Message();
        m.setForeignId(foreignid);
        return Result.success(messageService.findByForeign(foreignid));
    }

    @GetMapping("/ucmt/{id}")
    public Result<?> getByAID(@PathVariable Long id){
        return Result.success(messageService.findByAid(id));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(messageService.list());
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Message> query = Wrappers.<Message>lambdaQuery().like(Message::getContent, name).orderByDesc(Message::getId);
        return Result.success(messageService.page(new Page<>(pageNum, pageSize), query));
    }
}

