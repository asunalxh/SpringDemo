package com.example.springdemo.mapper;

import com.example.springdemo.entity.Distribution;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DistributionMapper {

    @Select("select * from distribution")
    List<Distribution> getAllDistribution();

    @Delete("delete from distribution")
    void clearDistribution();

    @Insert("<script>" +
            "insert into distribution(market,warehouse,goods,num) values" +
            "<foreach collection='list' item='item' separator=','>" +
            "(#{item.market},#{item.warehouse},#{item.goods},#{item.num})" +
            "</foreach>" +
            "</script>")
    void insertDistributionList(List<Distribution> list);

    @Select("select goods,SUM(num) as num from distribution where market=#{id} group by goods")
    List<Distribution> getDistributionListOfMarketGroupByGoods(String id);

    @Select("select * from distribution where warehouse=#{id}")
    List<Distribution> getDistributionListOfWarehouse(String id);

 }
