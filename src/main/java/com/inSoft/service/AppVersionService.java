package com.inSoft.service;

import com.inSoft.pojo.AppVersion;

import java.util.List;

public interface AppVersionService {

    //条件查询
    List<AppVersion> listByCondition(AppVersion appVersion);

    //保存数据
    boolean save(AppVersion appVersion);

    //根据id查询
    AppVersion getById(Integer id);

    //更新数据
    boolean update(AppVersion appVersion);

    //删除
    boolean remove(Integer id);

    //批量删除
    boolean removeBatch(List<Integer> ids);

    //恢复
    boolean recovery(Integer id);

    //批量恢复
    boolean recoveryBatch(List<Integer> ids);

    //获取分类下指定数量版本信息
    List<AppVersion> getListByCid(Integer appCatId, int num);
}
