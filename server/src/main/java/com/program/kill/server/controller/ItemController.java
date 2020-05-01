package com.program.kill.server.controller;

import com.program.kill.model.entity.ItemKill;
import com.program.kill.server.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Author: 李开鹏
 * @Date: 2020/4/30 13:35
 * @Version 1.0
 * 待秒杀商品controller
 */
@Controller
public class ItemController {
    //日志
    private static final Logger log = LoggerFactory.getLogger(ItemController.class);

    //前缀
    private static final String prefix = "item";

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = {"/", "/index", prefix + "/list", prefix + "index.html"}, method = RequestMethod.GET)
    public String list(ModelMap modelMap) { //使用ModelMap将处理之后的数据塞回到前端页面
        try {
            //获取待秒杀商品列表
            List<ItemKill> itemKill = itemService.getItemKill();
            modelMap.put("itemKill", itemKill);
            log.info("获取待秒杀商品列表-数据：{}", itemKill);
        } catch (Exception e) {
            log.error("获取待秒杀商品列表-发生异常", e.fillInStackTrace());
            return "redirect:/base/error";
        }
        return "list";
    }

    //获取商品详情
    @RequestMapping(value = prefix+"/detail/{id}",method = RequestMethod.GET)
    public String detail(@PathVariable Integer id,ModelMap modelMap) {
        if (id == null || id <= 0){
            return "redirect:/base/error";
        }
        try {
            ItemKill itemDetail = itemService.getItemDetail(id);
            modelMap.put("itemDetail",itemDetail);
        }catch (Exception e) {
            log.error("获取待秒杀商品的详情-发生异常：id={}",id,e.fillInStackTrace());
            return "redirect:/base/error";
        }
        return "info";
    }
}
