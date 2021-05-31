package com.example.springdemo.mapper;

import com.example.springdemo.entity.Distance;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface DistanceMapper {

    @Select("select * from distance where market = #{id}")
    @Results({
            @Result(property = "dis", column = "dis"),
            @Result(property = "market", column = "market",
                    one = @One(select = "com.example.springdemo.mapper.MarketMapper.getMarketByID", fetchType = FetchType.LAZY)),
            @Result(property = "warehouse", column = "warehouse",
                    one = @One(select = "com.example.springdemo.mapper.WarehouseMapper.getWarehouseByID", fetchType = FetchType.LAZY))
    })
    List<Distance> getDistanceByMarket(String id);

    @Select("select * from distance where warehouse = #{id}")
    @Results({
            @Result(property = "dis", column = "dis"),
            @Result(property = "market", column = "market",
                    one = @One(select = "com.example.springdemo.mapper.MarketMapper.getMarketByID", fetchType = FetchType.LAZY)),
            @Result(property = "warehouse", column = "warehouse",
                    one = @One(select = "com.example.springdemo.mapper.WarehouseMapper.getWarehouseByID", fetchType = FetchType.LAZY))
    })
    List<Distance> getDistanceByWarehouse(String id);

    @Insert("<script> replace into distance(market,warehouse,dis) values" +
            "<foreach collection='list' item='item' separator=','>" +
            "(#{item.market.id},#{item.warehouse.id},#{item.dis})" +
            "</foreach>" +
            "</script>")
    void replaceDistanceList(List<Distance> distanceList);
}
