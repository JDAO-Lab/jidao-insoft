package com.inSoft.service;

import com.inSoft.pojo.WebNavCat;

import java.util.List;

public interface WebNavCatService {

    //根据条件查询数据
    List<WebNavCat> listByCondition(WebNavCat webNavCat);

    //保存数据
    boolean save(WebNavCat webNavCat);

    //根据id获取数据
    WebNavCat getById(Integer id);

    //更新数据
    boolean update(WebNavCat webNavCat);

    //删除
    boolean remove(Integer id);

    //批量删除
    boolean removeBatch(List<Integer> ids);

    //恢复
    boolean recovery(Integer id);

    //批量恢复
    boolean recoveryBatch(List<Integer> ids);

    //select数据
    List<WebNavCat> getSelect();

    //获取id首位
    Integer getFirstId();

}
