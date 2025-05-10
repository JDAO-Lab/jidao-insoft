package com.inSoft.service.impl;

import com.inSoft.mapper.SysConfigMapper;
import com.inSoft.pojo.SysConfig;
import com.inSoft.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysConfigServiceImpl implements SysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    public List<SysConfig> getAllSysConfigs() {
        return sysConfigMapper.getByAll();
    }

    public String getConfigValueByName(String name) {
        return sysConfigMapper.getConfigByName(name);
    }

    public boolean updateConfigValueByName(String name, String value) {
        return sysConfigMapper.updateConfigValueByName(name, value);
    }

}
