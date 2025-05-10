package com.inSoft.mapper;

import com.inSoft.mapper.provider.WebPushProductProvider;
import com.inSoft.pojo.WebPushProduct;
import com.inSoft.pojo.WebPushProduct;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WebPushProductMapper {

    //根据条件查询
    @SelectProvider(type = WebPushProductProvider.class, method = "listByCondition")
    List<WebPushProduct> listByCondition(WebPushProduct webPushProduct);

    //保存数据
    @Insert("insert into web_push_product(name,logo,href,sort,created_at) values(#{name},#{logo},#{href},#{sort},#{createdAt})")
    boolean save(WebPushProduct webPushProduct);

    //根据id获取数据
    @Select("select * from web_push_product where id=#{id}")
    WebPushProduct getById(Integer id);

    //更新数据
    @UpdateProvider(type = WebPushProductProvider.class, method = "update")
    boolean update(WebPushProduct webPushProduct);

    //删除
    @Delete("delete from web_push_product where id=#{id}")
    boolean remove(Integer id);

    //批量删除
    @DeleteProvider(type = WebPushProductProvider.class, method = "removeBatch")
    boolean removeBatch(List<Integer> ids);

    //查询所有数据
    @Select("select id,name,logo,href,sort from web_push_product order by sort asc limit 9")
    List<WebPushProduct> pushList();
}
