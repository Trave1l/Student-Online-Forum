package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.News;
import com.example.demo.mapper.NewsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NewsService extends ServiceImpl<NewsMapper, News> {

    @Resource
    private NewsMapper newsMapper;

}