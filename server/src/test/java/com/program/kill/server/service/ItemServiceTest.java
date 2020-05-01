package com.program.kill.server.service;

import com.program.kill.model.entity.ItemKill;
import com.program.kill.model.mapper.ItemKillMapper;
import com.program.kill.server.MainApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: 李开鹏
 * @Date: 2020/5/1 22:30
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class ItemServiceTest {
    @Autowired(required = false)
    private ItemKillMapper itemKillMapper;

    @Test
    public void testShowAllItemKill() throws Exception {
        List<ItemKill> itemKill = itemKillMapper.selectAllItemKill();
        for (ItemKill kill : itemKill) {

            System.out.println(itemKill);
        }
    }

    @Test
    public void getItemDetail() throws Exception {
        ItemKill itemKill = itemKillMapper.selectByPrimaryKey(1);
        System.out.println(itemKill);
    }

}