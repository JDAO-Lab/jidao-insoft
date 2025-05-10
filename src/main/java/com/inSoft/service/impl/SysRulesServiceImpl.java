package com.inSoft.service.impl;

import com.inSoft.mapper.SysRulesMapper;
import com.inSoft.pojo.SysRules;
import com.inSoft.service.SysRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRulesServiceImpl implements SysRulesService {

    @Autowired
    private SysRulesMapper sysRulesMapper;

    @Override
    public String getRulesById(Integer id){
        return sysRulesMapper.getRulesById(id);
    }

    public SysRules getById(Integer id){
        return sysRulesMapper.getById(id);
    }

    public List<SysRules> list(){
        return sysRulesMapper.list();
    };

    public List<SysRules> listByCondition(SysRules sysRules){
        return sysRulesMapper.listByCondition(sysRules);
    };

    public List<SysRules> selectRules(){
        return sysRulesMapper.selectRules();
    };


    public boolean save(SysRules sysRules){
        return sysRulesMapper.save(sysRules);
    };

    public boolean update(SysRules sysRules){
        return sysRulesMapper.update(sysRules);
    };

    public boolean remove(Integer id){
        //管理员禁止删除
        if (id == 1) {
            return false;
        }
        return sysRulesMapper.remove(id);
    };

    public boolean removeBatch(List<Integer> ids){
        //为空则不删除
        if (ids == null || ids.size() == 0) {
            return false;
        }
        //ids中有1则提示不能删除
        if (ids.contains(1)) {
            return false;
        }
        return sysRulesMapper.removeBatch(ids);
    };

    public boolean recovery(Integer id){
        return sysRulesMapper.recovery(id);
    };

    public boolean recoveryBatch(List<Integer> ids){
        //为空则不删除
        if (ids == null || ids.size() == 0) {
            return false;
        }
        return sysRulesMapper.recoveryBatch(ids);
    };

}
