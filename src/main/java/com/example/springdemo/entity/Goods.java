package com.example.springdemo.entity;

import lombok.Data;

/**
 * @created: 2021/05/26 10:38
 * @description:货物对象
 */
@Data
public class Goods {
    String id;
    String name;
    String barcode;
    String price;
    String unit;
    String classify;
    String classifyName;

    public void setData(String id, String name, String barcode, String price, String unit, String classify) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.price = price;
        this.unit = unit;
        this.classify = classify;
    }
}
