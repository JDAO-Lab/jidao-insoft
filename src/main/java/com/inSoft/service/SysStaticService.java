package com.inSoft.service;

import com.inSoft.pojo.SysStatic;

import java.util.List;
import java.util.Map;

public interface SysStaticService {

    boolean save(SysStatic sysStatic);

    boolean update(SysStatic sysStatic);

    int searchByNameAndTypeInToady(Integer type, Map<String,String> startDateAndEndTime);

    double getTypeValueByDateRange(Integer type,Map<String,String> startDateAndEndTime);

    double sumTypeValue(Integer type);

    List<SysStatic> listByCondition(Integer type,Map<String,String> startDateAndEndTime);

}
