package com.inSoft.service;

import com.inSoft.pojo.Income;

import java.util.List;

public interface IncomeService {

    //获取所有数据
    List<Income> list();

    //根据条件查询数据
    List<Income> listByCondition(Income income);

    //保存数据
    boolean save(Income income);

    //根据id获取数据
    Income getById(Integer id);

    //更新
    boolean update(Income income);

    //删除
    boolean remove(Integer id);

    //批量删除
    boolean removeBatch(List<Integer> ids);

    //恢复
    boolean recovery(Integer id);

    //批量恢复
    boolean recoveryBatch(List<Integer> ids);

    //显示增值服务到页面
    List<Income> getPricesToView(Integer num);
}
