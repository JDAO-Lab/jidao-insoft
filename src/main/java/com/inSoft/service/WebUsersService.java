package com.inSoft.service;

import com.inSoft.pojo.WebUsers;

import java.util.List;

/**
 * 用户信息服务类
 */
public interface WebUsersService {

    //获取数据
    List<WebUsers> list();

    //根据id获取数据
    WebUsers getById(Integer id);

    //条件查询数据
    List<WebUsers> listByCondition(WebUsers webUsers);

    //更新数据
    boolean update(WebUsers webUsers);

    //批量禁用
    boolean disableBatch(List<Integer> ids);

    //批量启用
    boolean enableBatch(List<Integer> ids);

    //根据token获取详情
    WebUsers getUserInfoByToken(String token);

    boolean isOnlyPhone(String phone);

    boolean reg(WebUsers webUsers);

    WebUsers getUserInfoByPhone(String phone);

    boolean isOnlineIn(String userToken);

    boolean updatePassword(String phone, String password);

    boolean updateToken(int id, String token);
}
