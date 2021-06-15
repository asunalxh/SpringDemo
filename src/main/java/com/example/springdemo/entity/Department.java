package com.example.springdemo.entity;

import lombok.Data;

import java.util.List;

/**
 * @created: 2021/05/25 21:35
 * @description: 超市对象
 */

@Data
public class Department {
    String id;
    String name;
    String address;
    String tel;
    String contacts;

    List<Relative> relativeList;

    public void setData(String id, String name, String address, String tel, String contacts) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.contacts = contacts;
    }
}
