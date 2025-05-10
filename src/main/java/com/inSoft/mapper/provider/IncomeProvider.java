package com.inSoft.mapper.provider;

import com.inSoft.pojo.Income;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.stream.Collectors;

public class IncomeProvider {

    //条件查询
    public String listByCondition(Income income)
    {
        return new SQL(){{
            SELECT("*"); // 多条件查询
            FROM("web_income");
            if (income.getName()!=null){
                WHERE("name like CONCAT('%', #{name}, '%')");
            }
            if (income.getDescription()!=null){
                WHERE("description like CONCAT('%', #{description}, '%')");
            }
            ORDER_BY("id DESC");
        }}.toString();
    }

    //更新数据
    public String update(Income income)
    {
        return new SQL(){{
            UPDATE("web_income");
            if (income.getName()!=null){
                SET("name=#{name}");
            }
            if (income.getDescription()!=null){
                SET("description=#{description}");
            }
            if (income.getPrice()!=null){
                SET("price=#{price}");
            }
            if (income.getIcon()!=null){
                SET("icon=#{icon}");
            }
            if (income.getDiscount()!=null){
                SET("discount=#{discount}");
            }
            if (income.getDuration()!=null){
                SET("duration=#{duration}");
            }
            if (income.getCreatedAt()!=null){
                SET("created_at=#{createdAt}");
            }
            if (income.getUpdatedAt()!=null){
                SET("updated_at=#{updatedAt}");
            }
            //删除状态
            if (income.getIsDeleted()!=null){
                SET("is_deleted=#{isDeleted}");
            }
            if (income.getPrivilege()!=null){
                SET("privilege=#{privilege}");
            }
            if (income.getSort()!=null){
                SET("sort=#{sort}");
            }
            WHERE("id=#{id}");
        }}.toString();
    }

    //批量删除 只更新is_deleted
    public String removeBatch(List<Integer> ids)
    {
        String idStr = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return new SQL() {{
            UPDATE("web_income");
            SET("is_deleted = 1");
            WHERE("id IN (" + idStr + ")");
        }}.toString();
    }

    //批量恢复
    public String recoveryBatch(List<Integer> ids)
    {
        String idStr = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return new SQL() {{
            UPDATE("web_income");
            SET("is_deleted = 0");
            WHERE("id IN (" + idStr + ")");
        }}.toString();
    }

}
