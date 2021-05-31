package com.example.springdemo.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import com.example.springdemo.entity.Department;
import com.example.springdemo.entity.Distribution;
import com.example.springdemo.entity.Relative;
import com.example.springdemo.service.DepartmentService;
import com.example.springdemo.service.DistributionService;
import com.example.springdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 李鑫豪
 * @created: 2021/05/27 16:13
 * @description: JSON数据交互接口
 */

@RestController
public class DataController {

    @Autowired
    private DistributionService distributionService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    @SaCheckRole("总部")
    @RequestMapping("distributionData")
    public List<Distribution> distributionData() {
        return distributionService.getAllDistribution();
    }


    @SaCheckRole("超市")
    @GetMapping("applyList")
    public List<Map<String, String>> applyList(Model model) {
        String userID = (String) StpUtil.getLoginId();
        String id = userService.getUserByID(userID).getInfo();

        Department market = departmentService.getDepartmentByID(id, DepartmentService.MARKET);
        List<Map<String, String>> ans = new ArrayList<>();
        for (Relative r : market.getRelativeList()) {
            Map<String, String> mp = new HashMap<>();
            mp.put("id", r.getGoods().getId());
            mp.put("num", r.getNum());
            ans.add(mp);
        }
        return ans;
    }

    @SaCheckRole("仓库")
    @GetMapping("supplyList")
    public List<Map<String, String>> supplyList(Model model) {
        String userID = (String) StpUtil.getLoginId();
        String id = userService.getUserByID(userID).getInfo();

        Department market = departmentService.getDepartmentByID(id, DepartmentService.WAREHOUSE);
        List<Map<String, String>> ans = new ArrayList<>();
        for (Relative r : market.getRelativeList()) {
            Map<String, String> mp = new HashMap<>();
            mp.put("id", r.getGoods().getId());
            mp.put("num", r.getNum());
            ans.add(mp);
        }
        return ans;
    }
}
