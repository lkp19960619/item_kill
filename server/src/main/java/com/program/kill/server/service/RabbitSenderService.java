package com.program.kill.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * RabbitMQ发送消息的服务
 * @Author: 李开鹏
 * @Date: 2020/5/2 21:16
 * @Version 1.0
 **/
@Service
public class RabbitSenderService {

    public static final Logger log = LoggerFactory.getLogger(RabbitSenderService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment env;

    /**
     * 秒杀成功，异步发送邮件消息通知
     * @param orderNo   订单编号
     */
    public void sendKillSuccessEmailMsg(String orderNo){
        log.info("秒杀成功异步发送邮件消息通知-准备发送消息：{}",orderNo);
        //TODO：发送消息
        try {
            //TODO：rabbitmq发送消息的逻辑
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter()); //设置消息格式
            rabbitTemplate.setExchange(env.getProperty("mq.kill.item.success.email.exchange"));    //设置交换机
            rabbitTemplate.setRoutingKey(env.getProperty("mq.kill.item.success.email.routing.key"));
            rabbitTemplate.convertAndSend(MessageBuilder.withBody(orderNo.getBytes()));
        }catch (Exception e) {
            log.error("秒杀成功异步发送邮件消息通知-发生异常：{}",orderNo,e.fillInStackTrace());
        }
    }
}
