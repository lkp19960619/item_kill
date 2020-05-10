package com.program.kill.model.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Item {
    @Excel(name = "主键")
    private Integer id;

    @Excel(name = "商品名称")
    private String name;

    @Excel(name = "商品编号")
    private String code;

    @Excel(name = "商品单价")
    private Long stock;

    @Excel(name = "购买时间",format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date purchaseTime;

    @Excel(name = "商品状态")
    private Integer isActive;

    @Excel(name = "商品上架时间",format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @Excel(name = "更新时间",format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
}