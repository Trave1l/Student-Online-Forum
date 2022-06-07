package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Report;
import com.example.demo.mapper.ReportMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ComplainService extends ServiceImpl<ReportMapper, Report> {

    @Resource
    private ReportMapper ReportMapper;
}