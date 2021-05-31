package com.example.springdemo.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.example.springdemo.entity.Department;
import com.example.springdemo.entity.Distance;
import com.example.springdemo.service.DepartmentService;
import com.example.springdemo.service.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: 李鑫豪
 * @created: 2021/05/27 22:48
 * @description: 总部管理超市仓库部门信息
 */

@SaCheckRole("总部")
@Controller
public class headerDepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DistanceService distanceService;

    /**
     * 所有超市信息列表
     *
     * @param model
     * @return
     */
    @GetMapping("marketList")
    public String marketList(Model model) {
        List<Department> list = departmentService.getAll(DepartmentService.MARKET);
        model.addAttribute("departmentList", list);
        model.addAttribute("isMarket", true);
        return "header/departmentList";
    }

    /**
     * 所有仓库信息列表
     *
     * @param model
     * @return
     */
    @GetMapping("warehouseList")
    public String warehouseList(Model model) {
        List<Department> list = departmentService.getAll(DepartmentService.WAREHOUSE);
        model.addAttribute("departmentList", list);
        model.addAttribute("isMarket", false);
        return "header/departmentList";
    }


    /**
     * 某个超市的详细信息界面
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("marketInfo")
    public String marketInfo(@RequestParam("id") String id, Model model) {
        Department market = departmentService.getDepartmentByID(id, DepartmentService.MARKET);
        model.addAttribute("id", market.getId());
        model.addAttribute("name", market.getName());
        model.addAttribute("address", market.getAddress());
        model.addAttribute("tel", market.getTel());
        model.addAttribute("contacts", market.getContacts());

        List<Map<String, String>> disList = distanceService.getDistanceMapByMarket(id);
        model.addAttribute("disList", disList);
        model.addAttribute("isMarket", true);
        model.addAttribute("isEdit", true);
        return "header/departmentInfo";
    }

    /**
     * 某个仓库的详细信息界面
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("warehouseInfo")
    public String warehouseInfo(@RequestParam("id") String id, Model model) {
        Department warehouseInfo = departmentService.getDepartmentByID(id, DepartmentService.WAREHOUSE);
        model.addAttribute("id", warehouseInfo.getId());
        model.addAttribute("name", warehouseInfo.getName());
        model.addAttribute("address", warehouseInfo.getAddress());
        model.addAttribute("tel", warehouseInfo.getTel());
        model.addAttribute("contacts", warehouseInfo.getContacts());

        List<Map<String, String>> disList = distanceService.getDistanceMapByWarehouse(id);
        model.addAttribute("disList", disList);
        model.addAttribute("isMarket", false);
        model.addAttribute("isEdit", true);
        return "header/departmentInfo";
    }


    /**
     * 更新超市信息
     *
     * @param id
     * @param name
     * @param address
     * @param tel
     * @param contacts
     * @param warehouseList
     * @param numList
     * @return
     */
    @PostMapping("updateMarket")
    public String updateMarket(@RequestParam("updateType") String updateType,
                               @RequestParam("id") String id,
                               @RequestParam("name") String name,
                               @RequestParam("address") String address,
                               @RequestParam("tel") String tel,
                               @RequestParam("contacts") String contacts,
                               @RequestParam("warehouse") List<String> warehouseList,
                               @RequestParam("num") List<String> numList) {

        Department market = new Department();
        market.setData(id, name, address, tel, contacts);

        if (updateType.equals("insert"))
            departmentService.insertDepartment(market, DepartmentService.MARKET);
        else
            departmentService.updateDepartment(market, DepartmentService.MARKET);

        List<Distance> list = new ArrayList<>();
        for (int i = 0; i < numList.size(); i++) {
            Distance distance = new Distance();
            distance.setDis(numList.get(i));

            Department warehouse = new Department();
            warehouse.setId(warehouseList.get(i));
            distance.setWarehouse(warehouse);
            distance.setMarket(market);
            list.add(distance);
        }

        distanceService.replaceDistanceList(list);

        return "redirect:/marketInfo?id=" + id;
    }

    /**
     * 更新仓库信息
     *
     * @param id
     * @param name
     * @param address
     * @param tel
     * @param contacts
     * @param marketList
     * @param numList
     * @return
     */
    @PostMapping("updateWarehouse")
    public String updateWarehouse(@RequestParam("updateType") String updateType,
                                  @RequestParam("id") String id,
                                  @RequestParam("name") String name,
                                  @RequestParam("address") String address,
                                  @RequestParam("tel") String tel,
                                  @RequestParam("contacts") String contacts,
                                  @RequestParam("market") List<String> marketList,
                                  @RequestParam("num") List<String> numList) {

        Department warehouse = new Department();
        warehouse.setData(id, name, address, tel, contacts);

        if (updateType.equals("insert"))
            departmentService.insertDepartment(warehouse, DepartmentService.WAREHOUSE);
        else
            departmentService.updateDepartment(warehouse, DepartmentService.WAREHOUSE);

        List<Distance> list = new ArrayList<>();
        for (int i = 0; i < numList.size(); i++) {
            Distance distance = new Distance();
            distance.setDis(numList.get(i));

            Department market = new Department();
            market.setId(marketList.get(i));
            distance.setWarehouse(warehouse);
            distance.setMarket(market);
            list.add(distance);
        }

        distanceService.replaceDistanceList(list);

        return "redirect:/warehouseInfo?id=" + id;
    }

    /**
     * 删除超市信息
     *
     * @param id 超市ID
     * @return 重定向至超市列表
     */
    @GetMapping("delMarket")
    public String delMarket(@RequestParam("id") String id) {
        departmentService.delDepartment(id, DepartmentService.MARKET);
        return "redirect:/marketList";
    }

    /**
     * 删除仓库信息
     *
     * @param id 仓库ID
     * @return 重定向至仓库ID
     */
    @GetMapping("delWarehouse")
    public String delWarehouse(@RequestParam("id") String id) {
        departmentService.delDepartment(id, DepartmentService.WAREHOUSE);
        return "redirect:/warehouseList";
    }


    @GetMapping("addMarket")
    public String addMarket(Model model) {
        model.addAttribute("isMarket", true);
        model.addAttribute("isEdit", false);

        List<Department> warehouseList = departmentService.getAll(DepartmentService.WAREHOUSE);
        model.addAttribute("warehouseList", warehouseList);

        return "header/departmentInfo";
    }

    @GetMapping("addWarehouse")
    public String addWarehouse(Model model) {
        model.addAttribute("isMarket", false);
        model.addAttribute("isEdit", false);

        List<Department> warehouseList = departmentService.getAll(DepartmentService.MARKET);
        model.addAttribute("marketList", warehouseList);

        return "header/departmentInfo";
    }

}
