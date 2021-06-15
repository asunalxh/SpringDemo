package com.example.springdemo.mapper;

import com.example.springdemo.entity.Department;
import com.example.springdemo.entity.Relative;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Map;

@Mapper
public interface MarketMapper {

    @Select("select * from market")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "relativeList", column = "id",
                    many = @Many(select = "com.example.springdemo.mapper.MarketMapper.getApplyListByMarket", fetchType = FetchType.LAZY)),
     })
    List<Department> getAllMarket();

    @Select("select * from market where id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "relativeList", column = "id",
                    many = @Many(select = "com.example.springdemo.mapper.MarketMapper.getApplyListByMarket", fetchType = FetchType.LAZY))
    })
    Department getMarketByID(String id);


    @Select("select * from apply where market = #{id}")
    @Results({
            @Result(property = "num", column = "num"),
            @Result(property = "goods", column = "goods",
                    one = @One(select = "com.example.springdemo.mapper.GoodsMapper.getGoodsByID"))
    })
    List<Relative> getApplyListByMarket(String id);

    @Select("Select COUNT(DISTINCT market) from apply")
    int getApplyCount();

    @Select("select COUNT(*) from market")
    int getMarketCount();

    @Delete("delete from market where id=#{id}")
    void delMarket(@Param("id") String id);

    @Insert("insert into market(id,name,address,tel,contacts) values(#{id},#{name},#{address},#{tel},#{contacts})")
    void insertMarket(Department department);

    @Update("update market set name=#{name},address=#{address},tel=#{tel},contacts=#{contacts} where id=#{id}")
    void updateMarket(Department department);

    @Delete("delete from apply where market=#{id}")
    void deleteApplyOfMarket(String id);

    @Insert("<script>" +
            "insert into apply(market,goods,num) values" +
            "<foreach collection='list' item='item' separator=','>" +
            "(#{item.market},#{item.goods},#{item.num})" +
            "</foreach>" +
            "</script>")
    void insertApplyList(List<Map<String, String>> list);
}
