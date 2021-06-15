package com.example.springdemo.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.example.springdemo.entity.*;
import com.example.springdemo.service.*;
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
 * @created: 2021/05/25 22:56
 * @description: 总部功能管理
 */

@SaCheckRole("总部")
@Controller
public class HeaderController {


    @Autowired
    DistributionService distributionService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("applyInfoItem")
    public String applyInfo(@RequestParam("page") String pageStr, Model model) {
        int page = Integer.parseInt(pageStr);
        List<Department> list = departmentService.getApplyMarketList();
        if (list.size() >= page) {
            Department d = list.get(page - 1);
            model.addAttribute("title", d.getName());
            model.addAttribute("relativeList", d.getRelativeList());
        }
        model.addAttribute("isApply", true);
        model.addAttribute("page", page);
        model.addAttribute("maxPage", list.size());
        return "header/headerIndexItem";
    }

    @GetMapping("supplyInfoItem")
    public String supplyInfoItem(@RequestParam("page") String pageStr, Model model) {
        int page = Integer.parseInt(pageStr);
        List<Department> list = departmentService.getSupplyWarehouseList();
        if (list.size() >= page) {
            Department d = list.get(page - 1);
            model.addAttribute("title", d.getName());
            model.addAttribute("relativeList", d.getRelativeList());
        }
        model.addAttribute("isApply", false);
        model.addAttribute("page", page);
        model.addAttribute("maxPage", list.size());
        return "header/headerIndexItem";
    }

    @GetMapping("distribution")
    public String distribution(Model model) {
        List<Department> warehouseList = departmentService.getAll(DepartmentService.WAREHOUSE);
        List<Department> marketList = departmentService.getAll(DepartmentService.MARKET);
        model.addAttribute("warehouseList", warehouseList);
        model.addAttribute("marketList", marketList);
        return "header/distribution";
    }

    @PostMapping("replaceDistribution")
    public String replaceDistribution(@RequestParam("market") List<String> marketList,
                                      @RequestParam("warehouse") List<String> warehouseList,
                                      @RequestParam("goods") List<String> goodsList,
                                      @RequestParam("num") List<String> numList){
        List<Distribution> list = new ArrayList<>();
        for(int i = 0; i < numList.size(); i++){
            Distribution distribution = new Distribution();
            distribution.setGoods(goodsList.get(i));
            distribution.setMarket(marketList.get(i));
            distribution.setWarehouse(warehouseList.get(i));
            distribution.setNum(numList.get(i));
            list.add(distribution);
        }

        distributionService.replaceDistributionList(list);
        return "redirect:/distribution";
    }


}
