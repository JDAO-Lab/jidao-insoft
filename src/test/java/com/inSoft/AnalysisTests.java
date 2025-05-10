package com.inSoft;

import com.inSoft.enums.StaticTypeEnum;
import com.inSoft.utils.SysStaticUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

//测试统计模块
@SpringBootTest
public class AnalysisTests {

    @Autowired
    private SysStaticUtil sysStaticUtil;

    private static final Random RANDOM = new Random();

    @Test
    public void testViewCount() {
        String name = StaticTypeEnum.getByCode(0).getValue();
        int randomValue = getRandomValueInRange(1, 1000);
        sysStaticUtil.analysis(0, name, randomValue);
    }

    @Test
    public void testRegCount() {
        String name = StaticTypeEnum.getByCode(1).getValue();
        int randomValue = getRandomValueInRange(1, 1000);
        sysStaticUtil.analysis(1, name, randomValue);
    }

    @Test
    public void testPaidUsers() {
        String name = StaticTypeEnum.getByCode(4).getValue();
        int randomValue = getRandomValueInRange(1, 100);
        sysStaticUtil.analysis(4, name, randomValue);
    }

    @Test
    public void testActiveUsers() {
        String name = StaticTypeEnum.getByCode(4).getValue();
        int randomValue = getRandomValueInRange(1, 10);
        sysStaticUtil.analysis(2, name, randomValue);
    }

    @Test
    public void testApiCalls() {
        String name = StaticTypeEnum.getByCode(5).getValue();
        int randomValue = getRandomValueInRange(1, 1000);
        sysStaticUtil.analysis(5, name, randomValue);
    }

    @Test
    public void testOrders() {
        String name = StaticTypeEnum.getByCode(6).getValue();
        sysStaticUtil.analysis(3,name,1000);
    }

    private int getRandomValueInRange(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

    @Test
    public void testTotalIncome() {
        String name = StaticTypeEnum.getByCode(6).getValue();
        sysStaticUtil.analysis(6,name,100);
    }

    @Test
    public void testPlayTotalCount() {
        String name = StaticTypeEnum.getByCode(7).getValue();
        sysStaticUtil.analysis(7,name,1);
    }

    @Test
    public void testInstallCount() {
        String name = StaticTypeEnum.getByCode(8).getValue();
        sysStaticUtil.analysis(8,name,12);
    }
}
