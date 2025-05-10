package com.inSoft;

import com.inSoft.pojo.SysLog;
import com.inSoft.service.SysLogService;
import com.inSoft.utils.DeBugUtils;
import com.inSoft.utils.SysLogUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class SysLogTests {
    @Autowired
    private SysLogUtils sysLogUtils;

    @Autowired
    private SysLogService sysLogService;

    @Test
    public void testSaveLog(){
        sysLogUtils.saveLog("测试单元:testSaveLog lucky",0);
    }

    @Test
    public void testGetLog(){
        SysLog sysLog = new SysLog();
        List<SysLog> logs = sysLogService.list(sysLog);
        DeBugUtils.print(logs);
    }


}
