package com.example.springdemo.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.example.springdemo.entity.*;
import com.example.springdemo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 李鑫豪
 * @created: 2021/05/25 16:07
 * @description:
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DistributionService distributionService;

    @Autowired
    private GoodsService goodsService;

    @PostMapping("/loginCheck")
    public String loginCheck(@RequestParam("id") String id, @RequestParam("password") String password) {
        User user = userService.getUserByID(id);
        if (user != null && user.getPassword().equals(password)) {
            StpUtil.setLoginId(id);
            return "redirect:/index";
        }

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout() {
        StpUtil.logout();
        return "redirect:/login";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @SaCheckLogin
    @GetMapping("index")
    public String index(Model model) {
        String userID = (String) StpUtil.getLoginId();
        User user = userService.getUserByID(userID);
        if (user.getType().equals("总部")) {
            int marketCount = departmentService.getDepartmentCount(DepartmentService.MARKET);
            int warehouseCount = departmentService.getDepartmentCount(DepartmentService.WAREHOUSE);
            int applyCount = departmentService.getSubmitCount(DepartmentService.MARKET);
            int supplyCount = departmentService.getSubmitCount(DepartmentService.WAREHOUSE);
            model.addAttribute("marketCount", marketCount);
            model.addAttribute("warehouseCount", warehouseCount);
            model.addAttribute("applyCount", applyCount);
            model.addAttribute("supplyCount", supplyCount);
            return "header/headerIndex";
        } else if (user.getType().equals("超市")) {
            String id = user.getInfo();
            Map<String, String> distributionMap = distributionService.getDistributionMapOfMarketGroupByGoods(id);
            Department department = departmentService.getDepartmentByID(id, DepartmentService.MARKET);

            List<Map<String, String>> list = new ArrayList<>();

            for (Relative r : department.getRelativeList()) {
                Map<String, String> mp = new HashMap<>();
                mp.put("goodsName", r.getGoods().getName());
                mp.put("applyNum", r.getNum());

                if (distributionMap.containsKey(r.getGoods().getId())) {
                    int applyNum = Integer.parseInt(r.getNum());
                    int distributionNum = Integer.parseInt(distributionMap.get(r.getGoods().getId()));
                    int percent = distributionNum * 100 / applyNum;

                    mp.put("distributionNum", distributionMap.get(r.getGoods().getId()));
                    mp.put("percent", String.valueOf(percent));
                } else {
                    mp.put("distributionNum", "0");
                    mp.put("percent", "0");
                }
                list.add(mp);
            }

            model.addAttribute("list", list);
            return "market/marketIndex";
        } else if (user.getType().equals("仓库")) {
            String id = user.getInfo();
            List<Department> marketList = departmentService.getAll(DepartmentService.MARKET);
            List<Goods> goodsList = goodsService.getAllGoods();
            Map<String, Map<String, String>> distributionMap = distributionService.getDistributionMapListOfWarehouse(id);

            List<Map<String, Object>> list = new ArrayList<>();
            for (Department department : marketList) {
                if (distributionMap.containsKey(department.getId())) {
                    Map<String, Object> mp = new HashMap<>();
                    mp.put("marketName", department.getName());

                    List<Map<String, String>> tempList = new ArrayList<>();
                    for (Goods goods : goodsList) {
                        if (distributionMap.get(department.getId()).containsKey(goods.getId())) {
                            Map<String, String> map = new HashMap<>();
                            map.put("goodsID", goods.getId());
                            map.put("goodsName", goods.getName());
                            map.put("goodsBarcode", goods.getBarcode());
                            map.put("goodsPrice", goods.getPrice());
                            map.put("goodsUnit", goods.getUnit());
                            map.put("num", distributionMap.get(department.getId()).get(goods.getId()));
                            tempList.add(map);
                        }
                    }
                    mp.put("distributionList", tempList);
                    list.add(mp);
                }
            }

            model.addAttribute("list", list);
            return "warehouse/warehouseIndex";
        }
        return null;
    }

    @GetMapping("profile")
    public String profile(Model model){
        String id = (String) StpUtil.getLoginId();
        User user = userService.getUserByID(id);

        List<Department> marketList = departmentService.getAll(DepartmentService.MARKET);
        List<Department> warehouseList = departmentService.getAll(DepartmentService.WAREHOUSE);

        model.addAttribute("id", user.getId());
        model.addAttribute("name", user.getName());
        model.addAttribute("type", user.getType());
        model.addAttribute("info", user.getInfo());
        model.addAttribute("remark", user.getRemark());

        model.addAttribute("marketList", marketList);
        model.addAttribute("warehouseList", warehouseList);

        model.addAttribute("changeAble",false);


        if(user.getType().equals("总部")){
            model.addAttribute("leftIndexPage","header/navFragment");
            model.addAttribute("topIndexPage","header/navFragment");
            model.addAttribute("leftIndexContent","headerLeftIndex");
            model.addAttribute("topIndexContent","headerTopIndex");
        }else if(user.getType().equals("超市")){
            model.addAttribute("leftIndexPage","market/navFragment");
            model.addAttribute("topIndexPage","market/navFragment");
            model.addAttribute("leftIndexContent","marketLeftIndex");
            model.addAttribute("topIndexContent","marketTopIndex");
        }else if(user.getType().equals("仓库")){
            model.addAttribute("leftIndexPage","warehouse/navFragment");
            model.addAttribute("topIndexPage","warehouse/navFragment");
            model.addAttribute("leftIndexContent","warehouseLeftIndex");
            model.addAttribute("topIndexContent","warehouseTopIndex");
        }

        return "profile";
    }

    @PostMapping("updateUser")
    public String updateUser(@RequestParam("id") String id,
                             @RequestParam("name") String name,
                             @RequestParam("password") String password,
                             @RequestParam("type") String type,
                             @RequestParam("info") String info,
                             @RequestParam("remark") String remark) {
        User user = new User();
        user.setData(id, name, password, info, type, remark);
        userService.updateUser(user);
        return "redirect:/userInfo?id=" + id;
    }
}
