package com.example.springdemo.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.example.springdemo.entity.Classify;
import com.example.springdemo.entity.Goods;
import com.example.springdemo.service.GoodsService;
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
 * @author: 李鑫豪
 * @created: 2021/05/27 22:44
 * @description: 总部管理商品信息
 */
@SaCheckRole("总部")
@Controller
public class HeaderGoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 所有商品信息列表
     *
     * @param model
     * @return
     */
    @GetMapping("goodsList")
    public String goodsList(Model model) {
        List<Goods> list = goodsService.getAllGoods();
        model.addAttribute("goodsList", list);
        return "header/goodsList";
    }

    /**
     * 添加商品界面
     * @param model
     * @return
     */
    @GetMapping("addGoods")
    public String addGoods(Model model){
        List<Classify> list = goodsService.getAllClassify();
        model.addAttribute("classifyList",list);
        model.addAttribute("isEdit",false);
        return "header/goodsInfo";
    }

    /**
     * 某个商品的详细信息界面
     *
     * @param id    商品ID
     * @param model
     * @return
     */
    @GetMapping("goodsInfo")
    public String goodsInfo(@RequestParam("id") String id, Model model) {
        Goods goods = goodsService.getGoodsByID(id);
        List<Classify> list = goodsService.getAllClassify();

        model.addAttribute("classifyList", list);
        model.addAttribute("id", goods.getId());
        model.addAttribute("name", goods.getName());
        model.addAttribute("barcode", goods.getBarcode());
        model.addAttribute("classify", goods.getClassify());
        model.addAttribute("price", goods.getPrice());
        model.addAttribute("unit", goods.getUnit());
        model.addAttribute("isEdit",true);
        return "header/goodsInfo";
    }

    /**
     * 更新商品信息
     * @param id 商品ID
     * @param name 商品名称
     * @param barcode 商品条码
     * @param classify 商品分类
     * @param price 商品价格
     * @param unit 单位
     * @return
     */
    @PostMapping("updateGoods")
    public String updateGoods(@RequestParam("id") String id,
                              @RequestParam("name") String name,
                              @RequestParam("barcode") String barcode,
                              @RequestParam("classify") String classify,
                              @RequestParam("price") String price,
                              @RequestParam("unit") String unit) {

        Goods goods = new Goods();
        goods.setData(id, name, barcode, price, unit, classify);
        goodsService.updateGoods(goods);
        return "redirect:/goodsInfo?id=" + id;
    }

    /**
     * 添加商品信息
     * @param id 商品ID
     * @param name 商品名称
     * @param barcode 商品条码
     * @param classify 商品分类
     * @param price 商品价格
     * @param unit 单位
     * @return 重定向至商品信息页面
     */
    @PostMapping("insertGoods")
    public String insertGoods(@RequestParam("id") String id,
                              @RequestParam("name") String name,
                              @RequestParam("barcode") String barcode,
                              @RequestParam("classify") String classify,
                              @RequestParam("price") String price,
                              @RequestParam("unit") String unit) {

        Goods goods = new Goods();
        goods.setData(id, name, barcode, price, unit, classify);
        goodsService.insertGoods(goods);
        return "redirect:/goodsInfo?id=" + id;
    }

    /**
     * 删除商品信息
     * @param id 商品ID
     * @return 重定向至商品列表
     */
    @GetMapping("delGoods")
    public String delGoods(@RequestParam("id") String id){
        goodsService.delGoods(id);
        return "redirect:/goodsList";
    }

    /**
     * 所有商品分类列表信息
     *
     * @param model
     * @return
     */
    @GetMapping("classifyList")
    public String classifyList(Model model) {
        List<Classify> classifyList = goodsService.getAllClassify();
        model.addAttribute("classifyList", classifyList);

        return "header/classifyList";
    }

    /**
     * 分类详细信息
     * @param id
     * @param model
     * @return
     */
    @GetMapping("classifyInfo")
    public String classifyInfo(@RequestParam("id") String id, Model model) {
        Classify classify = goodsService.getClassifyByID(id);
        List<Goods> goodsList = classify.getGoodsList();
        List<Map<String, String>> list = new ArrayList<>();
        for (Goods goods : goodsList) {
            Map<String, String> mp = new HashMap<>();
            mp.put("id", goods.getId());
            mp.put("name", goods.getName());
            mp.put("barcode", goods.getBarcode());
            mp.put("price", goods.getPrice());
            mp.put("unit", goods.getUnit());
            mp.put("classify", classify.getName());
            list.add(mp);
        }
        model.addAttribute("name", classify.getName());
        model.addAttribute("goodsList", list);

        return "header/classifyInfo";
    }
}
