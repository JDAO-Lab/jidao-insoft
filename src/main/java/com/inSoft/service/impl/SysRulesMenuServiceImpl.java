package com.inSoft.service.impl;

import com.inSoft.mapper.SysRulesMenuMapper;
import com.inSoft.pojo.SysRulesMenu;
import com.inSoft.service.SysRulesMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysRulesMenuServiceImpl implements SysRulesMenuService {


    @Autowired
    private SysRulesMenuMapper sysRulesMenuMapper;

    public boolean save(SysRulesMenu sysRulesMenu){
        return sysRulesMenuMapper.save(sysRulesMenu);
    }

    public boolean update(SysRulesMenu sysRulesMenu){
        return sysRulesMenuMapper.update(sysRulesMenu);
    };


    public SysRulesMenu getById(Integer id){
        return sysRulesMenuMapper.getById(id);
    };

    public List<SysRulesMenu> getUserRulesByIds(String ids){
        return sysRulesMenuMapper.getUserRulesByIds(ids);
    };

    @Override
    public List<Object> generateMenuTree(List<SysRulesMenu> allMenus) {
        return getChildrenData(allMenus, 0);
    }

    public List<SysRulesMenu> list(){
        return sysRulesMenuMapper.list();
    }

    /**
     * 获取Dtree搜许的select数据
     * @param allMenus
     * @param rules
     * @return
     */
    public List<Object> getSelectTree(List<SysRulesMenu> allMenus, String rules) {
        if (rules == null || rules.isEmpty()) {
            rules = ""; // 如果rules为null，则默认为空字符串
        }
        return getSelectChildrenData(allMenus, 0, rules.split(","));
    }

    /**
     * 获取dtree可用的select数据
     * @param allMenus
     * @param pid
     * @return
     */
    private List<Object> getSelectChildrenData(List<SysRulesMenu> allMenus, int pid, String[] rulesArr) {
        List<Object> tree = new ArrayList<>();
        for (SysRulesMenu menu : allMenus) {
            if (menu.getPid() == pid) {
                Map<String, Object> menuObj = new HashMap<>();
                menuObj.put("id", menu.getId());
                menuObj.put("pid", menu.getPid());
                menuObj.put("title", menu.getTitle());

                if (rulesArr.length > 0 && Arrays.asList(rulesArr).contains(String.valueOf(menu.getId()))) {
                    menuObj.put("checkArr", "1"); // 如果id存在于rules中，则选中状态为1
                } else {
                    menuObj.put("checkArr", "0");
                }

                List<Object> children = getSelectChildrenData(allMenus, menu.getId(), rulesArr);
                if (!children.isEmpty()) {
                    menuObj.put("children", children);
                }
                tree.add(menuObj);
            }
        }
        return tree;
    }

    public int getSonByPid(Integer pid){
        return sysRulesMenuMapper.getSonByPid(pid);
    };

    public int getSonByPidBatch(List<Integer> ids){
        return sysRulesMenuMapper.getSonByPidBatch(ids);
    };

    /**
     * 单次删除数据
     * @param id
     * @return
     */
    public boolean remove(Integer id){
        return sysRulesMenuMapper.remove(id);
    };

    /**
     * 批量删除
     * @param ids
     * @return
     */
    public boolean removeBatch(List<Integer> ids){
        return sysRulesMenuMapper.removeBatch(ids);
    };


    /**
     * 获取后台管理系统的菜单数据
     * @param allMenus
     * @param pid
     * @return
     */
    private List<Object> getChildrenData(List<SysRulesMenu> allMenus, int pid) {
        List<Object> tree = new ArrayList<>();
        for (SysRulesMenu menu : allMenus) {
            if (menu.getPid() == pid) {
                Map<String, Object> menuObj = new HashMap<>();
                menuObj.put("id", menu.getId());
                menuObj.put("title", menu.getTitle());
                menuObj.put("icon", "layui-icon " + menu.getIcon());
                if (pid != 0) {
                    menuObj.put("openType", menu.getTarget());
                    menuObj.put("href", menu.getHref());
                }
                List<Object> children = getChildrenData(allMenus, menu.getId());
                if (!children.isEmpty()) {
                    menuObj.put("type", 0);
                    menuObj.put("children", children);
                }else{
                    menuObj.put("type", 1);
                }
                tree.add(menuObj);
            }
        }
        return tree;
    }

    @Override
    //根据路径获取id
    public int getIdByHref(String href) {
        Integer id = sysRulesMenuMapper.getIdByHref(href);
        return id != null ? id : -1; // 如果查询结果为null，则返回-1作为默认值
    }

    @Override
    public SysRulesMenu getMenuByHref(String href){
        return sysRulesMenuMapper.getMenuByHref(href);
    }
}