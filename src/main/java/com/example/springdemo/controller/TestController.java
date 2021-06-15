package com.example.springdemo.controller;

import com.example.springdemo.entity.Department;
import com.example.springdemo.entity.Goods;
import com.example.springdemo.entity.Relative;
import com.example.springdemo.service.DepartmentService;
import com.example.springdemo.service.DistributionService;
import com.example.springdemo.service.GoodsService;
import com.example.springdemo.service.UserService;
import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @created: 2021/05/29 22:48
 * @description:
 */
@RestController
public class TestController {

    @Autowired
    private DistributionService distributionService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public List<Map<String, String>> test() {
        String id = userService.getUserByID("S1").getInfo();

        Department market = departmentService.getDepartmentByID(id, DepartmentService.WAREHOUSE);
        List<Map<String, String>> ans = new ArrayList<>();
        for (Relative r : market.getRelativeList()) {
            Map<String, String> mp = new HashMap<>();
            mp.put("id", r.getGoods().getId());
            mp.put("num", r.getNum());
            ans.add(mp);
        };
        return ans;
    }

}
