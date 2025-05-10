package com.inSoft.mapper;

import com.inSoft.mapper.provider.WebCharacteristicProvider;
import com.inSoft.pojo.WebCharacteristic;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WebCharacteristicMapper {
    //根据条件查询
    @SelectProvider(type = WebCharacteristicProvider.class, method = "listByCondition")
    List<WebCharacteristic> listByCondition(WebCharacteristic webCharacteristic);

    //保存数据
    @Insert("insert into web_characteristic(title,thumb,description,short_description,sort,created_at) values(#{title},#{thumb},#{description},#{shortDescription},#{sort},#{createdAt})")
    boolean save(WebCharacteristic webCharacteristic);

    //根据id获取数据
    @Select("select * from web_characteristic where id=#{id}")
    WebCharacteristic getById(Integer id);

    //更新数据
    @UpdateProvider(type = WebCharacteristicProvider.class, method = "update")
    boolean update(WebCharacteristic webCharacteristic);

    //删除
    @Delete("delete from web_characteristic where id=#{id}")
    boolean remove(Integer id);

    //批量删除
    @DeleteProvider(type = WebCharacteristicProvider.class, method = "removeBatch")
    boolean removeBatch(List<Integer> ids);

    //查询所有数据
    @Select("select id,title,thumb,description,short_description,sort from web_characteristic order by sort asc limit 9")
    List<WebCharacteristic> pushList();
}
