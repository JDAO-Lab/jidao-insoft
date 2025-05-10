package com.inSoft.service;


import com.inSoft.pojo.WebUsersVipser;

public interface WebUsersVipserService {

    //根据uid获取会员数据
    WebUsersVipser getByUid(Integer uid);

    boolean save(WebUsersVipser newWebUsersVipser);

    boolean update(WebUsersVipser webUsersVipser);
}
