package com.program.kill.server.service.impl;

import com.program.kill.model.entity.User;
import com.program.kill.model.mapper.UserMapper;
import com.program.kill.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: 李开鹏
 * @Date: 2020/5/10 16:11
 * @Version 1.0
 **/
@Service
@Transactional  //事务，表示该类下所有的都受事务控制
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    @Cacheable(value = "users")
    public User selectById(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        System.out.println("*****使用缓存*****");
        return user;
    }

    @Override
    public void insert(User user) {
        int res = userMapper.insert(user);
        System.out.println(res);
    }
}
