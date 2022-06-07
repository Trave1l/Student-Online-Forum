package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Bbs;
import com.example.demo.entity.News;
import com.example.demo.mapper.BbsMapper;
import com.example.demo.mapper.NewsMapper;
import com.example.demo.service.BbsService;
import com.example.demo.service.NewsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/bbs")
public class BbsController {

    @Resource
    BbsMapper bbsMapper;
    @Resource
    private BbsService bbsService;

    @PostMapping
    public Result<?> save(@RequestBody Bbs bbs) {
        bbs.setTime(new Date());
        if(bbs.getFollow() == null){
            bbs.setFollow(1);
        }
        if(bbs.getGood() == null){
            bbs.setGood(0);
        }
        if(bbs.getBad() == null){
            bbs.setBad(0);
        }
        if(bbs.getSection() == null){
            bbs.setSection("NULL");
        }
        String str = bbs.getTitle();
        String cnt = bbs.getContent();
        String[] list = {"血腥", "国家级", "国家主席", "毛泽东", "澳独", "色情", "Pornhub", "pornhub", "暴力"};
        for(int i = 0; i < list.length; i++){
            String tmp = list[i];
            str =  str.replaceAll(tmp, "****");
            cnt = cnt.replaceAll(tmp, "****");
        }
        bbs.setStar(0);
        bbs.setTitle(str);
        bbs.setContent(cnt);
        bbs.setReport(0);
        bbsMapper.insert(bbs);
        return Result.success();
    }


    @PutMapping()
    public Result<?> follow(@RequestBody Bbs bbs) {
        int fo = bbs.getFollow();
        fo++;
        bbs.setFollow(fo);
        bbsMapper.updateById(bbs);
        return Result.success();
    }

    @PutMapping("/good")
    public Result<?> Good(@RequestBody Bbs bbs) {
        int go = bbs.getGood();
        go++;
        bbs.setGood(go);
        bbsMapper.updateById(bbs);
        return Result.success();
    }

    @PutMapping("/bad")
    public Result<?> Bad(@RequestBody Bbs bbs) {
        int bad = bbs.getBad();
        bad++;
        bbs.setBad(bad);
        bbsMapper.updateById(bbs);
        return Result.success();
    }

    @PutMapping("/report")
    public Result<?> Report(@RequestBody Bbs bbs) {
        bbs.setReport(1);
        bbsMapper.updateById(bbs);
        return Result.success();
    }

    @PutMapping("/star")
    public Result<?> Star(@RequestBody Bbs bbs) {
        int star = bbs.getStar();
        if(star == 0){
            bbs.setStar(1);
        }
        else{
            bbs.setStar(0);
        }
        bbsMapper.updateById(bbs);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id) {
        bbsMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(bbsMapper.selectById(id));
    }

    @GetMapping("/ubbs/{id}")
    public Result<?> getByUID(@PathVariable Long id){
        return Result.success(bbsService.findByForeign(id));
    }

    @GetMapping("/search/{id}/{title}")
    public Result<?> getByAidName(@PathVariable Long id, @PathVariable String title){
        return Result.success(bbsService.findByTitleId(id, title));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(bbsService.list());
    }

    @GetMapping("/section/{section}")
    public Result<?> findBySection(@PathVariable String section) {
        return Result.success(bbsService.findBySection(section));
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Bbs> wrapper = Wrappers.<Bbs>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Bbs::getTitle, search);
        }
        Page<Bbs> newsPage = bbsMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(newsPage);
    }
}

