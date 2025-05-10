package com.inSoft.service;

import com.inSoft.pojo.WebPost;

import java.util.List;

public interface WebPostRecoveryService {

    //回收站列表数据查询
    List<WebPost> listByCondition(WebPost webPost);

    //删除
    boolean remove(Integer id);

    //恢复
    boolean recovery(Integer id);

    //批量恢复
    boolean recoveryBatch(List<Integer> ids);

}
