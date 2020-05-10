package com.program.kill.server.service.impl;

import com.program.kill.model.entity.ItemKill;
import com.program.kill.model.entity.ItemKillSuccess;
import com.program.kill.model.mapper.ItemKillMapper;
import com.program.kill.model.mapper.ItemKillSuccessMapper;
import com.program.kill.server.enums.SysConstant;
import com.program.kill.server.service.KillService;
import com.program.kill.server.service.RabbitSenderService;
import com.program.kill.server.utils.RandomUtil;
import com.program.kill.server.utils.SnowFlake;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 李开鹏
 * @Date: 2020/5/2 10:50
 * @Version 1.0
 */
@Service
public class KillServiceImpl implements KillService {

    private static final Logger log = LoggerFactory.getLogger(KillServiceImpl.class);

    private static final SnowFlake snowFlake = new SnowFlake(2, 3);

    @Autowired(required = false)
    private ItemKillMapper itemKillMapper;

    @Autowired(required = false)
    private ItemKillSuccessMapper itemKillSuccessMapper;

    @Autowired
    private RabbitSenderService rabbitSenderService;


    /**
     * 商品秒杀核心业务逻辑的处理
     *
     * @param killId
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public Boolean killItem(Integer killId, Integer userId) throws Exception {
        Boolean result = false;
        //判断当前用户是否已经抢购过当前商品
        if (itemKillSuccessMapper.countByKillUserId(killId, userId) <= 0) {
            //查询待秒杀商品详情
            ItemKill itemKill = itemKillMapper.selectByPrimaryKey(killId);
            //判断是否可以被秒杀
            if (itemKill != null && itemKill.getCanKill() == 1) {
                //扣减库存
                int res = itemKillMapper.updateKillItem(killId);

                //扣减是否成功?是-生成秒杀成功的订单，同时通知用户秒杀成功的消息
                if (res > 0) {
                    commonRecordKillSuccessInfo(itemKill,userId);
                    result = true;
                }
            }
        } else {
            throw new Exception("您已经抢购过该商品！");
        }
        return result;
    }

    /**
     * 通用的方法-记录用户秒杀成功后生成的订单-并进行异步邮件消息的通知
     *
     * @param kill
     * @param userId
     * @throws Exception
     */
    private void commonRecordKillSuccessInfo(ItemKill kill, Integer userId) throws Exception {
        //TODO：记录抢购成功后生成的秒杀订单记录
        ItemKillSuccess itemKillSuccess = new ItemKillSuccess();
        //根据雪花算法生成订单编号
        String orderNo = String.valueOf(snowFlake.nextId());
//        itemKillSuccess.setCode(RandomUtil.generateOrderCode()); //传统时间戳+N位随机数
        itemKillSuccess.setCode(orderNo);
        itemKillSuccess.setItemId(kill.getItemId());
        itemKillSuccess.setKillId(kill.getId());
        itemKillSuccess.setUserId(userId.toString());
        itemKillSuccess.setStatus(SysConstant.OrderStatus.SuccessNotPayed.getCode().byteValue());
        itemKillSuccess.setCreateTime(DateTime.now().toDate());
        int res = itemKillSuccessMapper.insert(itemKillSuccess);
        if (res > 0){
            //TODO：进行异步邮件消息的通知-activeMQ+mail
            rabbitSenderService.sendKillSuccessEmailMsg(orderNo);
        }
    }
}
