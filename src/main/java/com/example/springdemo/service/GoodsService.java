package com.example.springdemo.service;

import com.example.springdemo.entity.Classify;
import com.example.springdemo.entity.Goods;
import com.example.springdemo.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @created: 2021/05/26 10:41
 * @description:
 */
@Service
public class GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    public List<Goods> getAllGoods() {
        return goodsMapper.getAllGoods();
    }

    public Goods getGoodsByID(String id){
        return goodsMapper.getGoodsByID(id);
    }

    /**
     * 从数据库查询所有商品信息
     * 将商品信息住存储位Map（key:value）形式，每个商品存入用一个map
     * 将所有map信息存入List列表
     * @return List 所有商品信息
     */
    public List<Map<String,String>> getGoodsMapList(){
        List<Goods> goodsList = goodsMapper.getAllGoods();
        List<Map<String, String>> list = new ArrayList<>();

        for (Goods goods : goodsList) {
            Map<String, String> mp = new HashMap<>();
            mp.put("id", goods.getId());
            mp.put("name", goods.getName());
            mp.put("barcode", goods.getBarcode());
            mp.put("price", goods.getPrice());
            mp.put("unit", goods.getUnit());
            mp.put("classify", goods.getClassify());
            list.add(mp);
        }
        return list;
    }


    /**
     *
     * @return 所有分类信息
     */
    public List<Classify> getAllClassify() {
        return goodsMapper.getAllClassify();
    }


    /**
     * 从数据库查询某个商品信息
     * @param id 商品ID
     * @return 商品信息
     */
    public Classify getClassifyByID(String id){
        return goodsMapper.getClassifyByID(id);
    }

    /**
     * 向数据库中插入一条商品数据
     * @param goods 商品信息
     */
    public void insertGoods(Goods goods){
        goodsMapper.insertGoods(goods);
    }

    /**
     * 更新数据库中的商品信息，
     * @param goods 商品信息
     */
    public void updateGoods(Goods goods){
        goodsMapper.updateGoods(goods);
    }

    public void delGoods(String id){
        goodsMapper.delGoods(id);
    }


    /**
     * 将分类信息存入 key : 数据 的map
     * 每个分类信息使用一个map
     * 将所有map存入一个List
     * @return 所有分类信息List
     */
    public List<Map<String,String>> getClassifyMapList(){
        List<Classify> classifyList = goodsMapper.getAllClassify();
        List<Map<String, String>> list = new ArrayList<>();

        for (Classify classify : classifyList) {
            Map<String, String> mp = new HashMap<>();
            mp.put("id", classify.getId());
            mp.put("name", classify.getName());
            list.add(mp);
        }
        return list;
    }

}
