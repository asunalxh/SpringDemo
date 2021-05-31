package com.example.springdemo.entity;

import lombok.Data;

/**
 * @author: 李鑫豪
 * @created: 2021/05/26 19:34
 * @description: 距离信息
 */

@Data
public class Distance {
    Department market = null;
    Department warehouse = null;
    String dis;
}
