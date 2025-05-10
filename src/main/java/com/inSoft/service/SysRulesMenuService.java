package com.inSoft.service;
import com.inSoft.pojo.SysRulesMenu;

import java.util.List;

public interface SysRulesMenuService {

    boolean save(SysRulesMenu sysRulesMenu);

    boolean update(SysRulesMenu sysRulesMenu);

    List<SysRulesMenu> list();

    List<Object> generateMenuTree(List<SysRulesMenu> allMenus);

    int getIdByHref(String href);

    SysRulesMenu getById(Integer id);

    boolean remove(Integer id);

    boolean removeBatch(List<Integer> ids);

    int getSonByPid(Integer pid);

    int getSonByPidBatch(List<Integer> ids);

    SysRulesMenu getMenuByHref(String href);

    List<Object> getSelectTree(List<SysRulesMenu> allMenus,String rules);

    List<SysRulesMenu> getUserRulesByIds(String ids);

}