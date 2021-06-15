package com.example.springdemo.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.example.springdemo.entity.Department;
import com.example.springdemo.entity.User;
import com.example.springdemo.service.DepartmentService;
import com.example.springdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @created: 2021/05/28 16:58
 * @description: 总部管理用户信息
 */
@SaCheckRole("总部")
@Controller
public class HeaderUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    /**
     * 所有用户信息列表
     *
     * @param model
     * @return
     */
    @GetMapping("userList")
    public String userList(Model model) {
        List<User> list = userService.getAllUser();
        List<Map<String, String>> headerList = new ArrayList<>();
        List<Map<String, String>> marketList = new ArrayList<>();
        List<Map<String, String>> warehouseList = new ArrayList<>();

        for (User user : list) {
            Map<String, String> mp = new HashMap<>();
            mp.put("id", user.getId());
            mp.put("name", user.getName());
            mp.put("remark", user.getRemark());
            mp.put("department", user.getDepartment());

            if (user.getType().equals("总部")) {
                headerList.add(mp);
            } else if (user.getType().equals("超市")) {
                marketList.add(mp);
            } else if (user.getType().equals("仓库")) {
                warehouseList.add(mp);
            }
        }
        model.addAttribute("headerList", headerList);
        model.addAttribute("marketList", marketList);
        model.addAttribute("warehouseList", warehouseList);
        return "header/userList";
    }

    /**
     * 注册用户界面
     *
     * @param model
     * @return
     */
    @GetMapping("userRegister")
    public String userRegister(Model model) {
        List<Department> warehouseList = departmentService.getAll(DepartmentService.WAREHOUSE);
        List<Department> marketList = departmentService.getAll(DepartmentService.MARKET);
        model.addAttribute("warehouseList", warehouseList);
        model.addAttribute("marketList", marketList);
        return "header/userRegister";
    }


    @PostMapping("addUser")
    public String addUser(@RequestParam("id") String id,
                          @RequestParam("name") String name,
                          @RequestParam("type") String type,
                          @RequestParam("info") String info,
                          @RequestParam("password") String password,
                          @RequestParam("remark") String remark) {
        User user = new User();
        user.setData(id, name, password, info, type, remark);
        userService.insertUser(user);
        return "redirect:/userInfo?id=" + id;
    }

    /**
     * 总部管理用户信息界面
     *
     * @param id 要管理的用户ID
     * @return 管理页面
     */
    @GetMapping("userInfo")
    public String userInfo(@RequestParam("id") String id, Model model) {
        List<Department> marketList = departmentService.getAll(DepartmentService.MARKET);
        List<Department> warehouseList = departmentService.getAll(DepartmentService.WAREHOUSE);
        User user = userService.getUserByID(id);

        model.addAttribute("id", user.getId());
        model.addAttribute("name", user.getName());
        model.addAttribute("type", user.getType());
        model.addAttribute("info", user.getInfo());
        model.addAttribute("remark", user.getRemark());

        model.addAttribute("marketList", marketList);
        model.addAttribute("warehouseList", warehouseList);

        model.addAttribute("leftIndexPage","header/navFragment");
        model.addAttribute("topIndexPage","header/navFragment");
        model.addAttribute("leftIndexContent","headerLeftIndex");
        model.addAttribute("topIndexContent","headerTopIndex");

        model.addAttribute("changeAble",true);


        return "profile";
    }


    @GetMapping("delUser")
    public String delUser(@RequestParam("id") String id) {
        userService.delUser(id);
        return "redirect:/userList";
    }



}
