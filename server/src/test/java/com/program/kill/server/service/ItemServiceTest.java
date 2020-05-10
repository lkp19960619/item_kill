package com.program.kill.server.service;

import com.program.kill.model.entity.ItemKill;
import com.program.kill.model.entity.User;
import com.program.kill.model.mapper.ItemKillMapper;
import com.program.kill.server.MainApplication;
import org.apache.commons.codec.digest.DigestUtils;
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
    @Autowired
    private ItemKillMapper itemKillMapper;

    @Autowired
    private KillService killService;

    @Autowired
    private UserService userService;

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

    @Test
    public void execute() throws Exception {
        Boolean res = killService.killItem(4, 10);
        System.out.println(res);
    }

    @Test
    public void testMd5() throws Exception{
        User user = new User();
        user.setId(1);
        user.setUserName("lkp");
        user.setPassword(DigestUtils.md5Hex("960619"));
        user.setPhone("17638530795");
        user.setEmail("1107440885@qq.com");
        user.setIsActive(null);
        user.setCreateTime(null);
        user.setUpdateTime(null);
        System.out.println(user);
        userService.insert(user);
    }

    @Test
    public void testCache()throws Exception{
        long t1 = System.nanoTime();
        User user = userService.selectById(1);
        System.out.println(user);
        long t2 = System.nanoTime();
        System.out.println(t2-t1);
    }
}