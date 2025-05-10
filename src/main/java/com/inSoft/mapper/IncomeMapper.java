package com.inSoft.mapper;

import com.inSoft.mapper.provider.IncomeProvider;
import com.inSoft.pojo.Income;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IncomeMapper {

    @Select("select * from web_income")
    List<Income> list();

    //根据条件查询
    @SelectProvider(type = IncomeProvider.class,method = "listByCondition")
    List<Income> listByCondition(Income income);

    //保存数据
    @Insert("insert into web_income(duration,name,icon,price,description,discount,created_at,privilege,sort) values(#{duration},#{name},#{icon},#{price},#{description},#{discount},#{createdAt},#{privilege},#{sort})")
    boolean save(Income income);

    //根据ID获取数据
    @Select("select * from web_income where id=#{id}")
    Income getById(Integer id);

    //更新数据
    @UpdateProvider(type = IncomeProvider.class,method = "update")
    boolean update(Income income);

    //删除 只更新is_deleted
    @Update("update web_income set is_deleted=1 where id=#{id}")
    boolean remove(Integer id);

    //批量删除 只批量更新is_deleted
    @UpdateProvider(type = IncomeProvider.class,method = "removeBatch")
    boolean removeBatch(List<Integer> ids);

    //恢复
    @Update("update web_income set is_deleted=0 where id=#{id}")
    boolean recovery(Integer id);

    //批量恢复
    @UpdateProvider(type = IncomeProvider.class,method = "recoveryBatch")
    boolean recoveryBatch(List<Integer> ids);

    //显示充值价格到界面
    @Select("select * from web_income where is_deleted=0 order by sort asc limit #{num}")
    List<Income> getPricesToView(Integer num);
}
