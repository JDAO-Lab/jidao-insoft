package com.inSoft.service;


import com.inSoft.pojo.WebNav;

import java.util.List;

public interface WebNavService{

    //根据条件查询数据
    List<WebNav> listByCondition(WebNav webNav);

    //统计数目
    Integer countByPid(Integer id);

    //批量统计所有数目
    Integer countByPids(List<Integer> ids);

    //获取顶级菜单数据
    List<WebNav> getTopMenu(Integer cid);

    //根据cid获取前台所需使用的数据
    List<Object> getNavDataByCid(Integer cid);

    //根据id获取数据
    WebNav getById(Integer id);

    //保存数据
    boolean save(WebNav webNav);

    //删除
    boolean remove(Integer id);

    //批量删除
    boolean removeBatch(List<Integer> ids);

    //更新
    boolean update(WebNav webNav);

    //根据path获取导航数据
    WebNav getByPath(String path);

}
