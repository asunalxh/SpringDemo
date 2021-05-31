package com.example.springdemo.service;

import com.example.springdemo.entity.Distance;
import com.example.springdemo.mapper.DistanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 李鑫豪
 * @created: 2021/05/26 19:37
 * @description: 距离查询服务
 */
@Service
public class DistanceService {

    @Autowired
    private DistanceMapper distanceMapper;

    /**
     * 从数据库获取与某一超市有关的距离信息
     * 将距离信息封装成Map形式
     * 将所有的Map放入List
     *
     * @param market 超市ID
     * @return List
     */
    public List<Map<String, String>> getDistanceMapByMarket(String market) {
        List<Distance> list = distanceMapper.getDistanceByMarket(market);
        return toMapList(list);
    }

    /**
     * 从数据库获取与某一仓库有关的距离信息
     * 将距离信息封装成Map形式
     * 将所有的Map放入List
     *
     * @param warehouse 仓库ID
     * @return List
     */
    public List<Map<String, String>> getDistanceMapByWarehouse(String warehouse) {
        List<Distance> list = distanceMapper.getDistanceByWarehouse(warehouse);
        return toMapList(list);
    }

    /**
     * 更新多个距离信息
     * 如果存在就进行更行
     * 如果不存在就插入
     *
     * @param list 距离信息
     */
    public void replaceDistanceList(List<Distance> list) {
        distanceMapper.replaceDistanceList(list);
    }

    /**
     * 将距离信息列表转化成对应的Map信息列表
     *
     * @param list 距离信息列表
     * @return mapList
     */
    private List<Map<String, String>> toMapList(List<Distance> list) {
        List<Map<String, String>> ans = new ArrayList<>();
        for (Distance d : list) {
            Map<String, String> mp = new HashMap<>();
            mp.put("marketID", d.getMarket().getId());
            mp.put("warehouseID", d.getWarehouse().getId());
            mp.put("marketName", d.getMarket().getName());
            mp.put("warehouseName", d.getWarehouse().getName());
            mp.put("dis", d.getDis());
            ans.add(mp);
        }
        return ans;
    }
}
