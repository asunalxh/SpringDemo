package com.example.springdemo.entity;

import lombok.Data;

import java.util.List;

/**
 * @created: 2021/05/26 16:11
 * @description:
 */
@Data
public class Classify {
    String id;
    String name;

    List<Goods> goodsList;
}
