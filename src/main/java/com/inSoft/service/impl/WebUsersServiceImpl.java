package com.inSoft.service.impl;

import com.inSoft.mapper.WebUsersMapper;
import com.inSoft.pojo.WebUsers;
import com.inSoft.service.WebUsersService;
import com.inSoft.utils.IPUtil;
import com.inSoft.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebUsersServiceImpl implements WebUsersService {

    @Autowired
    private WebUsersMapper webUsersMapper;

    public List<WebUsers> list(){
        return webUsersMapper.list();
    }

    public WebUsers getById(Integer id){
        return webUsersMapper.getById(id);
    }

    public List<WebUsers> listByCondition(WebUsers webUsers){
        return webUsersMapper.listByCondition(webUsers);
    }

    public boolean update(WebUsers webUsers){
        return webUsersMapper.update(webUsers);
    }

    public boolean enableBatch(List<Integer> ids){
        return webUsersMapper.enableBatch(ids);
    }

    @Override
    public WebUsers getUserInfoByToken(String token) {
        return webUsersMapper.getUserInfoByToken(token);
    }

    @Override
    public boolean isOnlyPhone(String phone) {
        return webUsersMapper.isOnlyPhone(phone);
    }

    @Override
    public boolean reg(WebUsers webUsers) {
        return webUsersMapper.reg(webUsers);
    }

    @Override
    public WebUsers getUserInfoByPhone(String phone) {
        return webUsersMapper.getUserInfoByPhone(phone);
    }

    @Override
    public boolean isOnlineIn(String userToken) {
        return webUsersMapper.isOnlineIn(userToken);
    }

    @Override
    public boolean updatePassword(String phone, String password) {
        return webUsersMapper.updatePassword(phone,password);
    }

    @Override
    public boolean updateToken(int id, String token) {
        return webUsersMapper.updateToken(id,token);
    }

    public boolean disableBatch(List<Integer> ids){
        return webUsersMapper.disableBatch(ids);
    }

}
