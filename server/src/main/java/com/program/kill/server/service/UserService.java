package com.program.kill.server.service;

import com.program.kill.model.entity.User;

/**
 * @Author: 李开鹏
 * @Date: 2020/5/10 16:10
 * @Version 1.0
 **/
public interface UserService {
    //注册
    void insert(User user);

    User selectById(Integer id);
}
