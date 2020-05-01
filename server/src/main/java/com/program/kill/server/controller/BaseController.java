package com.program.kill.server.controller;

import com.program.kill.api.enums.StatusCode;
import com.program.kill.api.response.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 李开鹏
 * @Date: 2020/4/29 23:01
 * @Version 1.0
 */
@Controller
@RequestMapping("base")
public class BaseController {
    public static final Logger log = LoggerFactory.getLogger(BaseController.class);

    @GetMapping("/welcome")
    public String welcome(String name, ModelMap modelMap){
        if(StringUtils.isBlank(name)){//判断参数是否为空
            name = "这是welcome";
        }
        modelMap.put("name",name);
        return "welcome";
    }

    //不会跳转页面
    @RequestMapping(value = "/data",method = RequestMethod.GET)
    @ResponseBody
    public String data(String  name){
        if(StringUtils.isBlank(name)){
            name = "这是welcome！";
        }
        return name;
    }

    @RequestMapping(value = "/response",method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse response(String name){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        if(StringUtils.isBlank(name)){
            name = "welcome!";
        }
        response.setData(name);
        return response;
    }

    @RequestMapping(value = "/error",method = RequestMethod.GET)
    public String error(){
        return "error";
    }
}
