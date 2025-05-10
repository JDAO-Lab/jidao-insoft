package com.inSoft.utils;

import com.inSoft.pojo.SysStatic;
import com.inSoft.service.SysStaticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class SysStaticUtil {

    @Autowired
    private SysStaticService sysStaticService;

    /**
     * 记录数据，通过输入的参数进行存储统计数据值
     * @param type
     * @param name
     * @param value
     */
    public void analysis(Integer type,String name,double value) {
        // 优先检查当日是否存在统计数据记录，如果存在则更新，如果不存在则创建
        String todayStr = DateUtils.getTodayStartAsString("yyyy-MM-dd");
        String staticName = todayStr+" "+name;
        Map<String, String> startDateAndEndTime = DateUtils.getStartDateAndEndTimeForDays(0, "yyyy-MM-dd HH:mm:ss");
//        DeBugUtils.print("统计区间数据："+startDateAndEndTime);
        Integer staticId = sysStaticService.searchByNameAndTypeInToady(type,startDateAndEndTime);
        SysStatic sysStatic = new SysStatic();
        sysStatic.setName(staticName);
        sysStatic.setType(type);
        sysStatic.setValue(value);
        if (staticId>0){
            //进行更新
            sysStatic.setId(staticId);
            sysStatic.setUpdatedAt(new Date());
            sysStaticService.update(sysStatic);
        }else {
            //进行创建
            sysStatic.setCreatedAt(new Date());
            sysStaticService.save(sysStatic);
        }
    }

}
