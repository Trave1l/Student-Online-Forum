package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.exception.CustomException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/user") //basic url for frontend
@CrossOrigin
public class UserController {
    @Resource
    UserMapper userMapper;
    @Resource
    UserService userService;

    //Post -- login
    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        queryWrapper.eq("password", user.getPassword());
        User res = userMapper.selectOne(queryWrapper);
        if (res == null) {
            return Result.error("-1", "用户名或密码错误");
        }
        return Result.success(res);
    }

    //Post -- login with emergency code
    @PostMapping("/login/emergency")
    public Result<?> loginEmergency(@RequestBody User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        queryWrapper.eq("emergency", user.getEmergency());
        User res = userMapper.selectOne(queryWrapper);
        if (res == null) {
            return Result.error("-1", "用户名或紧急码错误");
        }
        return Result.success(res);
    }

    //Post -- register
    @PostMapping("/register")
    public Result<?> register(@RequestBody User user){
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()));
        //There are no same username
        if(res != null){
            return Result.error("-1", "用户名重复");
        }
        //Random set nickname if null
        if(user.getNickname() == null){
            user.setNickname("默认用户" + new Random().nextInt(9999));
        }
        //set age if null
        if(user.getAge() == null){
            user.setAge(18);
        }
        //set unknown if null
        if(user.getSex() == null){
            user.setSex("未知");
        }
        //set ordinary of users
        if(user.getRole() == null){
            user.setRole(2);
        }
        user.setState(0);//set offline
        String str = getItemID(5);
        str = str.toUpperCase();//get emergency codes
        //Make sure there are no special symbols in username
        String tmp = user.getUsername();
        for(int i = 0; i < tmp.length(); i++){
            int ch = (int) tmp.charAt(i);
            if((ch >= 58 && ch <= 64) || (ch >= 91 && ch <= 96) || ch >= 123){
                tmp = tmp.replace(tmp.charAt(i), '_');
            }
        }
        String tmpPwd = user.getPassword();
        if(isAlphabetic(tmpPwd)){
            user.setPassword(tmpPwd);
        }else{
            return Result.error("-1", "密码过简单");
        }
        user.setUsername(tmp);
        user.setEmergency(str);
        user.setReport(0);
        user.setCnt(0);
        userMapper.insert(user);//insert the data into database
        return Result.success();
    }

    /**
     * Function: Determine whether are all numbers & alphabets or not
     * @param String s
     * @return boolean
     * */
    public static boolean isAlphabetic(String s){
        int num=0;
        int alph =0;
        for(int i = 0; i < s.length(); i++){
            int ch = (int) s.charAt(i);
            if(ch>=48 && ch<=57){
                num++;
            }else if(ch>=65 && ch <=90 || ch>=97 && ch<=122){
                alph++;
            }
        }
        if(num != s.length() && alph != s.length()){
            num = alph = 0;
            return true;
        }
        num = alph = 0;
        return false;
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
//      Fuzzy search -- function: .like()
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery().like(User::getNickname, search);
        if(StrUtil.isNotBlank(search)){
            wrapper.like(User::getNickname, search);
        }
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum,pageSize), wrapper);
        return Result.success(userPage);
    }

    //Update
    @PutMapping
    public Result<?> update(@RequestBody User user){
//        Insert into database
        userMapper.updateById(user);
        return Result.success();
    }

    //Update for the number of report
    @PutMapping("/report")
    public Result<?> report(@RequestBody User user){
        int tmp = user.getCnt();
        if(tmp > 20){
            user.setReport(2);
        }
        tmp++;
        user.setReport(1);
        user.setCnt(tmp);
//        Insert into database
        userMapper.updateById(user);
        return Result.success();
    }

    //Update state of user -- 1 for online, 0 for offline
    @PutMapping("/log")
    public Result<?> log(@RequestBody User user){
        user.setState(1);
//        Insert into database
        userMapper.updateById(user);
        return Result.success();
    }

    /*
    * Function GetItemID()
    * input: Integer N
    * Output:a string which length is 5, composed by numbers or alphabets
    * */
    private static String getItemID( int n )
    {
        String val = "";
        Random random = new Random();
        for ( int i = 0; i < n; i++ )
        {
            String str = random.nextInt( 2 ) % 2 == 0 ? "num" : "char";
            if ( "char".equalsIgnoreCase( str ) )
            { // Get alphabets
                int nextInt = random.nextInt( 2 ) % 2 == 0 ? 65 : 97;
                val += (char) ( nextInt + random.nextInt( 26 ) );
            }
            else if ( "num".equalsIgnoreCase( str ) )
            { // Get number
                val += String.valueOf( random.nextInt( 10 ) );
            }
        }
        return val;
    }

    //Update state of user -- 1 for online, 0 for offline
    @PutMapping("/quit")
    public Result<?> quit(@RequestBody User user){
        user.setState(0);
//        Insert into database
        userMapper.updateById(user);
        return Result.success();
    }

    //delete data
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id){
        userMapper.deleteById(id);
        return Result.success();
    }

    //Find user by id
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(userMapper.selectById(id));
    }

    //Find user by username
    @GetMapping("/name/{name}")
    public  Result<?> getByName(@PathVariable String name){
        return Result.success(userService.findByName(name));
    }

    //reset for password
    @PutMapping("/reset")
    public Result<?> reset(@RequestBody User user) {
        if (StrUtil.isBlank(user.getUsername())) {
            throw new CustomException("-1", "用户名不能为空");
        }
        User one = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()));
        if (one == null) {
            throw new CustomException("-1", "未找到用户");
        }
        one.setPassword(user.getPassword());
        return Result.success(userService.updateById(one));
    }
}
