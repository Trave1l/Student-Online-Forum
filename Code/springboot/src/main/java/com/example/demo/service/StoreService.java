package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Store;
import com.example.demo.mapper.StoreMapper;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StoreService extends ServiceImpl<StoreMapper, Store> {

    @Resource
    private StoreMapper Storemapper;

    public List<Store> findByBbsId(Long id) {
        LambdaQueryWrapper<Store> queryWrapper = Wrappers.<Store>lambdaQuery().eq(Store::getBbsId, id);
        List<Store> list = list(queryWrapper);
        return list;
    }

    public List<Store> findByUId(Long id) {
        LambdaQueryWrapper<Store> queryWrapper = Wrappers.<Store>lambdaQuery().eq(Store::getUserId, id);
        List<Store> list = list(queryWrapper);
        return list;
    }

    public List<Store> find(Long uid, Long bid) {
        LambdaQueryWrapper<Store> queryWrapper = Wrappers.<Store>lambdaQuery().eq(Store::getBbsId, bid);
        LambdaQueryWrapper<Store> queryWrapper1 = queryWrapper.eq(Store::getUserId, uid);
        List<Store> list = list(queryWrapper1);
        return list;
    }
}