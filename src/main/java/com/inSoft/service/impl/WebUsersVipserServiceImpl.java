package com.inSoft.service.impl;

import com.inSoft.mapper.WebUsersVipserMapper;
import com.inSoft.pojo.WebUsersVipser;
import com.inSoft.service.WebUsersVipserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebUsersVipserServiceImpl  implements WebUsersVipserService {

    @Autowired
    private WebUsersVipserMapper webUsersVipserMapper;

    public WebUsersVipser getByUid(Integer uid) {
        return webUsersVipserMapper.getByUid(uid);
    }

    @Override
    public boolean save(WebUsersVipser newWebUsersVipser) {
        return webUsersVipserMapper.save(newWebUsersVipser);
    }

    @Override
    public boolean update(WebUsersVipser webUsersVipser) {
        return webUsersVipserMapper.update(webUsersVipser);
    }

}
