package com.program.kill.model.entity;

import lombok.Data;

@Data
public class RandomCode {
    private Integer id;

    private String code;

    public RandomCode(Integer id, String code) {
        this.id = id;
        this.code = code;
    }

}