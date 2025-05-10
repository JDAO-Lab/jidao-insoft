package com.inSoft.service;

import com.inSoft.pojo.AppCat;

import java.util.List;

public interface AppCatService {

    //根据条件查询数据
    List<AppCat> listByCondition(AppCat appCat);

    //保存数据
    boolean save(AppCat appCat);

    //根据id获取数据
    AppCat getById(Integer id);

    //更新数据
    boolean update(AppCat appCat);

    //删除
    boolean remove(Integer id);

    //批量删除
    boolean removeBatch(List<Integer> ids);

    //恢复
    boolean recovery(Integer id);

    //批量恢复
    boolean recoveryBatch(List<Integer> ids);

    //select数据
    List<AppCat> getSelect();

    //获取id首位
    Integer getFirstId();

}
