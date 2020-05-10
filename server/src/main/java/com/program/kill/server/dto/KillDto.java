package com.program.kill.server.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: 李开鹏
 * @Date: 2020/5/2 10:08
 * @Version 1.0
 */
@Data
//代替toString方法
@ToString
public class KillDto implements Serializable {
    @NotNull
    private Integer killId;

    private Integer userId;

}
