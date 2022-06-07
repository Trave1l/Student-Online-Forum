package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.Complain;
import com.example.demo.mapper.ComplainMapper;
import com.example.demo.service.ComplainService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/complain")
public class ComplainController {

    @Resource
    ComplainMapper ComplainMapper;
    @Resource
    private ComplainService ComplainService;

    @PostMapping
    public Result<?> save(@RequestBody Complain Complain) {
        System.out.println(Complain);
        Complain.setTime(new Date());
        ComplainMapper.insert(Complain);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id) {
        ComplainMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(ComplainMapper.selectById(id));
    }


    @GetMapping
    public Result<?> findAll() {
        return Result.success(ComplainService.list());
    }


}

