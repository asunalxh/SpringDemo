package com.example.springdemo.mapper;

import com.example.springdemo.entity.Department;
import com.example.springdemo.entity.Relative;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Map;

@Mapper
public interface WarehouseMapper {

    @Select("select * from warehouse")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "relativeList", column = "id",
                    many = @Many(select = "com.example.springdemo.mapper.WarehouseMapper.getSupplyListByWarehouse", fetchType = FetchType.LAZY)),
    })
    List<Department> getAllWarehouse();

    @Select("select * from warehouse where id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "relativeList", column = "id",
                    many = @Many(select = "com.example.springdemo.mapper.WarehouseMapper.getSupplyListByWarehouse", fetchType = FetchType.LAZY))
    })
    Department getWarehouseByID(String id);


    @Select("select * from supply where warehouse = #{id}")
    @Results({
            @Result(property = "num", column = "num"),
            @Result(property = "goods", column = "goods",
                    one = @One(select = "com.example.springdemo.mapper.GoodsMapper.getGoodsByID"))
    })
    List<Relative> getSupplyListByWarehouse(String id);

    @Select("select COUNT(DISTINCT warehouse) from supply")
    int getSupplyCount();

    @Select("select COUNT(*) from warehouse")
    int getWarehouseCount();

    @Insert("insert into warehouse(id,name,address,tel,contacts) values(#{id}, #{name},#{address}, #{tel}, #{contacts})")
    void insertWarehouse(Department warehouse);

    @Delete("delete from warehouse where id=#{id}")
    void delWarehouse(@Param("id") String id);

    @Update("update warehouse set name=#{name},address=#{address},tel=#{tel},contacts=#{contacts} where id=#{id}")
    void updateWarehouse(Department department);

    @Delete("delete from supply where warehouse=#{id}")
    void deleteSupplyOfWarehouse(String id);

    @Insert("<script>" +
            "insert into supply(warehouse,goods,num) values" +
            "<foreach collection='list' item='item' separator=','>" +
            "(#{item.warehouse},#{item.goods},#{item.num})" +
            "</foreach>" +
            "</script>")
    void insertSupplyList(List<Map<String, String>> list);
}
