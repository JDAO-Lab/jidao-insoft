package com.inSoft.service;

import com.inSoft.pojo.SysAdmin;

import java.util.List;

public interface SysAdminService {
    List<SysAdmin> list();

    List<SysAdmin> listByCondition(SysAdmin sysAdmin);

    boolean save(SysAdmin sysAdmin);

    boolean update(SysAdmin sysAdmin);

    void loginUpDate(Integer id, String token);

    void delete(Integer id);

    SysAdmin getById(Integer id);

    SysAdmin loginAuthSql(String username, String password);

    int countUserByToken(String token);

    SysAdmin getUserInfoByToken(String token);

    int getRuleIdByToken(String token);

    int isOnlyUsername(String username);

    boolean remove(Integer id);

    boolean removeBatch(List<Integer> ids);

}