package com.inSoft.service;

import com.inSoft.pojo.WebPost;

import java.util.List;

public interface WebPostService {

    //根据条件查询
    List<WebPost> listByCondition(WebPost webPost);

    //保存数据
    boolean save(WebPost webPost);

    //根据id获取数据
    WebPost getById(Integer id);

    //更新数据
    boolean update(WebPost webPost);

    //删除
    boolean remove(Integer id);

    //批量删除
    boolean removeBatch(List<Integer> ids);

    //彻底删除
    boolean removed(Integer id);

    //恢复
    boolean recovery(Integer id);

    //批量恢复
    boolean recoveryBatch(List<Integer> ids);

    //阅读增加
    boolean views(Integer id);

    //根据分类获取指定数量
    List<WebPost> getListByCid(Integer webPostCatId, int num);

    //根据关键词查询结果
    List<WebPost> listBykeyword(String keyword, int num);
}
