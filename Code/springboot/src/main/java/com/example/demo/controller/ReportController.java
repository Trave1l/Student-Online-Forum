package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.Report;
import com.example.demo.mapper.ReportMapper;
import com.example.demo.service.ReportService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Resource
    ReportMapper ReportMapper;
    @Resource
    private ReportService ReportService;

    @PostMapping
    public Result<?> save(@RequestBody Report Report) {
        Report.setTime(new Date());
        ReportMapper.insert(Report);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id) {
        ReportMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(ReportMapper.selectById(id));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(ReportService.list());
    }

}

