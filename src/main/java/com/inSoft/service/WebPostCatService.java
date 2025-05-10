package com.inSoft.service;

import com.inSoft.pojo.WebPostCat;

import java.util.List;

public interface WebPostCatService {

    //根据条件查询
    List<WebPostCat> listByCondition(WebPostCat webPostCat);

    //保存数据
    boolean save(WebPostCat webPostCat);

    //根据id获取数据
    WebPostCat getById(Integer id);

    //更新数据
    boolean update(WebPostCat webPostCat);

    //删除
    boolean remove(Integer id);

    //批量删除
    boolean removeBatch(List<Integer> ids);

    //恢复
    boolean recovery(Integer id);

    //批量恢复
    boolean recoveryBatch(List<Integer> ids);

    //select
    List<WebPostCat> getSelect();

}
