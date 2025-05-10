package com.inSoft.service;

import com.inSoft.pojo.SysConfig;

import java.util.List;

public interface SysConfigService {

     //获取配置信息
     List<SysConfig> getAllSysConfigs();

     //根据name获取值
     String getConfigValueByName(String name);

     //根据name更新值
     boolean updateConfigValueByName(String name, String value);

}