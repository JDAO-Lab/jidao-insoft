package com.inSoft.service;


import com.inSoft.pojo.SysRules;

import java.util.List;

public interface SysRulesService {
    String getRulesById(Integer id);

    SysRules getById(Integer id);

    List<SysRules> list();

    List<SysRules> listByCondition(SysRules sysRules);

    List<SysRules> selectRules();

    boolean save(SysRules sysRules);

    boolean update(SysRules sysRules);

    boolean remove(Integer id);

    boolean removeBatch(List<Integer> ids);

    boolean recovery(Integer id);

    boolean recoveryBatch(List<Integer> ids);

}
