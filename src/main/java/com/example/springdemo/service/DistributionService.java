package com.example.springdemo.service;

import com.example.springdemo.entity.Distribution;
import com.example.springdemo.mapper.DistributionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.soap.Addressing;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 李鑫豪
 * @created: 2021/05/27 16:17
 * @description: 分配信息服务
 */
@Service
public class DistributionService {


    @Autowired
    private DistributionMapper distributionMapper;

    /**
     * 从数据库查询所有分配信息
     *
     * @return List 所有分配信息
     */
    public List<Distribution> getAllDistribution() {
        return distributionMapper.getAllDistribution();
    }

    /**
     * 替换分配信息
     *
     * @param list 分配信息
     */
    public void replaceDistributionList(List<Distribution> list) {
        distributionMapper.clearDistribution();
        distributionMapper.insertDistributionList(list);
    }

    /**
     * 从数据库查询某个超市获取到的分配信息
     * 将分配信息打包成 GoodsID : num 的形式
     *
     * @param market 超市ID
     * @return
     */
    public Map<String, String> getDistributionMapOfMarketGroupByGoods(String market) {
        Map<String, String> ans = new HashMap<>();
        List<Distribution> distributions = distributionMapper.getDistributionListOfMarketGroupByGoods(market);
        for (Distribution d : distributions) {
            ans.put(d.getGoods(), d.getNum());
        }
        return ans;
    }

    /**
     * 从数据库查询某个仓库的分配任务
     * 对于需要分配的超市，每个超市使用一个Map goodsName : num
     *
     * @param warehouse
     * @return
     */
    public Map<String, Map<String, String>> getDistributionMapListOfWarehouse(String warehouse) {
        Map<String, Map<String, String>> ans = new HashMap<>();
        List<Distribution> distributionList = distributionMapper.getDistributionListOfWarehouse(warehouse);

        for (Distribution d : distributionList) {
            if (!ans.containsKey(d.getMarket())) {
                ans.put(d.getMarket(), new HashMap<>());
            }
            ans.get(d.getMarket()).put(d.getGoods(), d.getNum());
        }
        return ans;
    }
}