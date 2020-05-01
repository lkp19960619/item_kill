package com.program.kill.server.service.impl;

import com.program.kill.model.entity.ItemKill;
import com.program.kill.model.mapper.ItemKillMapper;
import com.program.kill.server.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 李开鹏
 * @Date: 2020/4/30 13:43
 * @Version 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired(required = false)
    private ItemKillMapper itemKillMapper;
    /**
     * 获取待秒杀商品列表
     * @return
     * @throws Exception
     */
    @Override
    public List<ItemKill> getItemKill() throws Exception {
        return itemKillMapper.selectAllItemKill();
    }

    /**
     * 获取秒杀商品详情
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public ItemKill getItemDetail(Integer id) throws Exception {
        ItemKill itemDetail = itemKillMapper.selectByPrimaryKey(id);
        if(itemDetail==null){
            throw new Exception("获取秒杀商品详情-待秒杀商品记录不存在");
        }
        return itemDetail;
    }
}
