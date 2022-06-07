package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Store;
import com.example.demo.mapper.StoreMapper;
import com.example.demo.service.StoreService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Resource
    StoreMapper StoreMapper;
    @Resource
    private StoreService StoreService;

    @PutMapping()
    public Result<?> follow(@RequestBody Store Store) {
        int fo = Store.getFollow();
        fo++;
        Store.setFollow(fo);
        StoreMapper.updateById(Store);
        return Result.success();
    }

    @PostMapping
    public Result<?> save(@RequestBody Store Store) {
        Store.setTime(new Date());
        StoreMapper.insert(Store);
        return Result.success();
    }

    
    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id) {
        StoreMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(StoreMapper.selectById(id));
    }

    @GetMapping("/bbsID/{id}")
    public Result<?> getByBBSId(@PathVariable Long id) {
        return Result.success(StoreService.findByBbsId(id));
    }

    @GetMapping("/bbsUID/{id}")
    public Result<?> getByUID(@PathVariable Long id) {
        return Result.success(StoreService.findByUId(id));
    }

    @GetMapping("/StoreCheck/{uid}/{bid}")
    public Result<?> StoreCheck(@PathVariable Long uid, @PathVariable Long bid){
        return Result.success(StoreService.find(uid, bid));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(StoreService.list());
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Store> wrapper = Wrappers.<Store>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Store::getTitle, search);
        }
        Page<Store> newsPage = StoreMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(newsPage);
    }
}

