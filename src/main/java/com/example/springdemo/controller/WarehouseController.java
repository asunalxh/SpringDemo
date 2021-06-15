package com.example.springdemo.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import com.example.springdemo.entity.Goods;
import com.example.springdemo.service.DepartmentService;
import com.example.springdemo.service.GoodsService;
import com.example.springdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @created: 2021/05/29 21:34
 * @description: 仓库管理
 */
@SaCheckRole("仓库")
@Controller
public class WarehouseController {

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("supply")
    public String supply(Model model) {
        List<Goods> goodsList = goodsService.getAllGoods();
        model.addAttribute("goodsList", goodsList);
        return "warehouse/supply";
    }

    @PostMapping("replaceSupply")
    public String replaceApply(@RequestParam("id") List<String> goodsList,
                               @RequestParam("num") List<String> numList) {
        String userId = (String) StpUtil.getLoginId();
        String id = userService.getUserByID(userId).getInfo();

        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < numList.size(); i++) {
            Map<String, String> mp = new HashMap<>();
            mp.put("warehouse", id);
            mp.put("goods", goodsList.get(i));
            mp.put("num", numList.get(i));
            list.add(mp);
        }

        departmentService.replaceSubmitList(list, id, DepartmentService.WAREHOUSE);
        return "redirect:/supply";
    }
}
