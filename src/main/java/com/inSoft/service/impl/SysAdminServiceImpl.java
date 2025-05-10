package com.inSoft.service.impl;

import com.inSoft.mapper.SysAdminMapper;
import com.inSoft.pojo.SysAdmin;
import com.inSoft.service.SysAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysAdminServiceImpl implements SysAdminService {

    @Autowired
    private SysAdminMapper sysAdminMapper;

    @Override
    public List<SysAdmin> list() {
        return sysAdminMapper.list();
    }


    public List<SysAdmin> listByCondition(SysAdmin sysAdmin){
        return sysAdminMapper.listByCondition(sysAdmin);
    }

    @Override
    public boolean save(SysAdmin sysAdmin) {
       return sysAdminMapper.save(sysAdmin);
    }

    @Override
    public boolean update(SysAdmin sysAdmin) {
       return sysAdminMapper.update(sysAdmin);
    }

    @Override
    public void loginUpDate(Integer id, String token) {
        sysAdminMapper.loginUpDate(id, token);
    }

    @Override
    public void delete(Integer id) {
        sysAdminMapper.delete(id);
    }

    @Override
    public SysAdmin getById(Integer id) {
        return sysAdminMapper.getById(id);
    }

    @Override
    public SysAdmin loginAuthSql(String username, String password) {
        return sysAdminMapper.loginAuthSql(username, password);
    }

    @Override
    public int countUserByToken(String token) {
        return sysAdminMapper.countUserByToken(token);
    }

    @Override
    public SysAdmin getUserInfoByToken(String token) {
        return sysAdminMapper.getUserInfoByToken(token);
    }

    @Override
    public int getRuleIdByToken(String token) {
        return sysAdminMapper.getRuleIdByToken(token);
    }

    public int isOnlyUsername(String username){
        return sysAdminMapper.isOnlyUsername(username);
    };

    public boolean remove(Integer id){
        return sysAdminMapper.remove(id);
    }

    public boolean removeBatch(List<Integer> ids){
        return sysAdminMapper.removeBatch(ids);
    }

}