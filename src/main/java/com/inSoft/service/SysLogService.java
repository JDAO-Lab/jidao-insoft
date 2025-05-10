package com.inSoft.service;

import com.inSoft.pojo.SysLog;

import java.util.List;

public interface SysLogService {

     /**
      * 记录日志信息
      * @param sysLog
      */
     void addSysLog(SysLog sysLog);

     /**
      * 查询日志数据
      * @param sysLog
      * @return
      */
     List<SysLog> list(SysLog sysLog);

     List<SysLog> listByCondition(SysLog sysLog);

     /**
      * 获取指定ip、条数的最近日志信息
      */
     List<SysLog> listByIpAndLimit(String ip, int limit);


}