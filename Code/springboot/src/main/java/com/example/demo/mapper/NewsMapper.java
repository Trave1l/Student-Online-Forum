package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.News;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Param;


public interface NewsMapper extends BaseMapper<News> {
}

