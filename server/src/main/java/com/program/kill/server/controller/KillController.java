package com.program.kill.server.controller;

import com.program.kill.api.enums.StatusCode;
import com.program.kill.api.response.BaseResponse;
import com.program.kill.server.dto.KillDto;
import com.program.kill.server.service.KillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.awt.*;

/**
 * 秒杀controller
 *
 * @Author: 李开鹏
 * @Date: 2020/5/2 10:12
 * @Version 1.0
 */
@Controller
public class KillController {
    private static final Logger log = LoggerFactory.getLogger(KillController.class);

    private static final String prefix = "kill";

    @Autowired
    private KillService killService;

    /**
     * 秒杀商品
     * @param dto
     * @param result
     * @param session
     * @return
     */
    @RequestMapping(value = prefix + "/execute", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse execute(@RequestBody @Validated KillDto dto, BindingResult result, HttpSession session) {
        //@RequestBody的作用是可以将json格式的数据转换为java对象
        //@Validated的作用是用来做参数校验，必须搭配BindingResult使用

        if (result.hasErrors() || dto.getKillId() <= 0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
//        //从前台接收数据
//        Object uId = session.getAttribute("uid");
//        if(uId==null){
//            return new BaseResponse(StatusCode.UserNotLogin);
//        }
//        Integer userId = (Integer) uId;
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            Boolean res = killService.killItem(dto.getKillId(), dto.getUserId());
            if(!res){
                return new BaseResponse(StatusCode.Fail.getCode(),"商品已抢购完毕或不在抢购时间内");
            }
        }catch(Exception e){
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = prefix+"/execute/success",method = RequestMethod.GET)
    public String executeSuccess(){
        return "executeSuccess";
    }

    @RequestMapping(value = prefix+"/execute/fail",method=RequestMethod.GET)
    public String executeFail(){
        return "executeFail";
    }
}
