package com.program.kill.server.service;

import com.program.kill.model.entity.ItemKill;

import java.util.List;

/**
 * @Author: 李开鹏
 * @Date: 2020/4/30 13:43
 * @Version 1.0
 */
public interface ItemService {
    //获取秒杀商品
    List<ItemKill> getItemKill() throws Exception;
    //获取秒杀商品详情
    ItemKill getItemDetail(Integer id)throws Exception;
}
