package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Bbs;
import com.example.demo.entity.Message;
import com.example.demo.mapper.BbsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BbsService extends ServiceImpl<BbsMapper, Bbs> {

    @Resource
    private BbsMapper bbsMapper;

    public List<Bbs> findByForeign(Long aid) {
        LambdaQueryWrapper<Bbs> queryWrapper = Wrappers.<Bbs>lambdaQuery().eq(Bbs::getAid, aid);
        List<Bbs> list = list(queryWrapper);
        return list;
    }

    public List<Bbs> findBySection(String section) {
        LambdaQueryWrapper<Bbs> queryWrapper = Wrappers.<Bbs>lambdaQuery().eq(Bbs::getSection, section);
        List<Bbs> list = list(queryWrapper);
        return list;
    }

    public List<Bbs> findByTitleId(Long id, String title) {
        LambdaQueryWrapper<Bbs> queryWrapper = Wrappers.<Bbs>lambdaQuery().eq(Bbs::getAid, id).eq(Bbs::getTitle, title);
        List<Bbs> list = list(queryWrapper);
        return list;
    }
}