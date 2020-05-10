package com.program.kill.server.service;

/**
 * @Author: 李开鹏
 * @Date: 2020/5/2 10:49
 * @Version 1.0
 */
public interface KillService {
    Boolean killItem(Integer killId,Integer userId)throws Exception;
}
