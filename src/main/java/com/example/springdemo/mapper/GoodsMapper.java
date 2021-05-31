package com.example.springdemo.mapper;

import com.example.springdemo.entity.Classify;
import com.example.springdemo.entity.Goods;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsMapper {

    @Select("select goods.id,goods.name,barcode,goods.classify,price,unit,c.name as classifyName from goods left join classification as c on c.id = goods.classify")
    List<Goods> getAllGoods();


    @Select("select goods.id,goods.name,barcode,goods.classify,price,unit,c.name as classifyName from goods left join classification as c on c.id = goods.classify where goods.id = #{id}")
//    @Select("select * from goods")
    Goods getGoodsByID(String id);


    @Select("select * from classification")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "goodsList", column = "id",
                    many = @Many(select = "com.example.springdemo.mapper.GoodsMapper.getGoodsListByClassify"))
    })
    List<Classify> getAllClassify();


    @Select("select * from goods where classify = #{id}")
    List<Goods> getGoodsListByClassify(String id);

    @Select("select * from classification where id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "goodsList", column = "id",
                    many = @Many(select = "com.example.springdemo.mapper.GoodsMapper.getGoodsListByClassify"))
    })
    Classify getClassifyByID(String id);


    @Insert("insert into goods(id,name,barcode,classify,price,unit) values(#{id},#{name},#{barcode},#{classify},#{price},#{unit})")
    void insertGoods(Goods goods);

    @Update("update goods set name = #{name}, barcode = #{barcode}, classify=#{classify}, price = #{price}, unit = #{unit} where id = #{id} ")
    void updateGoods(Goods goods);

    @Delete("delete from goods where id=#{id}")
    void delGoods(String id);
}
