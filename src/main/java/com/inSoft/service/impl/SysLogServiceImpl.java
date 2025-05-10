package com.inSoft.service.impl;

import com.inSoft.mapper.SysLogMapper;

import com.inSoft.pojo.SysLog;
import com.inSoft.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SysLogServiceImpl implements SysLogService{

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public void addSysLog(SysLog sysLog) {
        sysLogMapper.add(sysLog);
    }

    @Override
    public List<SysLog> list(SysLog sysLog){
       return sysLogMapper.list();
    }

    @Override
    public List<SysLog> listByCondition(SysLog sysLog) {
        return sysLogMapper.listByCondition(sysLog);
    }

    @Override
    public List<SysLog> listByIpAndLimit(String ip, int limit){
        return sysLogMapper.listByIpAndLimit(ip,limit);
    }

}
