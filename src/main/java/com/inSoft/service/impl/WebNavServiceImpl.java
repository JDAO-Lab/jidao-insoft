package com.inSoft.service.impl;

import com.inSoft.mapper.WebNavMapper;
import com.inSoft.pojo.WebNav;
import com.inSoft.service.WebNavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WebNavServiceImpl implements WebNavService {

    @Autowired
    private WebNavMapper webNavMapper;

    //根据条件查询
    public List<WebNav> listByCondition(WebNav webNav) {
        return webNavMapper.listByCondition(webNav);
    }

    //统计总数
    public Integer countByPid(Integer id) {
        return webNavMapper.countByPid(id);
    }

    //统计总数
    public Integer countByPids(List<Integer> ids) {
        return webNavMapper.countByPids(ids);
    }

    //获取顶级菜单数据
    public List<WebNav> getTopMenu(Integer cid) {
        return webNavMapper.getTopMenu(cid);
    }

    //根据cid获取导航数据
    @Override
    public List<Object> getNavDataByCid(Integer cid) {
        List<WebNav> allMenus = webNavMapper.getNavDataByCid(cid);
        if (allMenus.size() == 0) {
            return null;
        }
        return getChildrenData(allMenus, 0);
    }

    //根据id获取数据
    public WebNav getById(Integer id) {
        return webNavMapper.getById(id);
    }

    //保存数据
    public boolean save(WebNav webNav) {
        return webNavMapper.save(webNav);
    }

    //删除
    public boolean remove(Integer id) {
        return webNavMapper.remove(id);
    }

    //批量删除
    public boolean removeBatch(List<Integer> ids) {
        return webNavMapper.removeBatch(ids);
    }

    //更新
    public boolean update(WebNav webNav) {
        return webNavMapper.update(webNav);
    }

    @Override
    public WebNav getByPath(String path) {
        return webNavMapper.getByPath(path);
    }

    //批量处理并生成前台所需的菜单数据结构
    private List<Object> getChildrenData(List<WebNav> allMenus, int pid) {
        List<Object> tree = new ArrayList<>();
        for (WebNav menu : allMenus) {
            if (menu.getPid() == pid) {
                Map<String, Object> menuObj = new HashMap<>();
                menuObj.put("id", menu.getId());
                menuObj.put("name", menu.getName());
                menuObj.put("sort", menu.getSort());
                menuObj.put("pid", menu.getPid());
                menuObj.put("type", menu.getType());
                List<Object> children = getChildrenData(allMenus, menu.getId());
                menuObj.put("children", children);
                if (children.size() == 0){
                    if (menu.getType() == 1){
                        menuObj.put("href", "/node?id="+menu.getId());
                    }else {
                        menuObj.put("href", menu.getPath());
                    }
                }else {
                    menuObj.put("href", "javascript:;");
                }
                tree.add(menuObj);
            }
        }
        return tree;
    }

}
