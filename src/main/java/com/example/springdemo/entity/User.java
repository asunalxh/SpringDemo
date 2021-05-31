package com.example.springdemo.entity;

import lombok.Data;

/**
 * @created: 2021/05/24 18:01
 * @description:
 */

@Data
public class User {
    String id;
    String name;
    String password;
    String info;
    String type;
    String remark;
    String department;

    public void setData(String id,String name,String password,String info,String type,String remark){
        this.id = id;
        this.name = name;
        this.password = password;
        this.info = info;
        this.type = type;
        this.remark = remark;
    }
}
