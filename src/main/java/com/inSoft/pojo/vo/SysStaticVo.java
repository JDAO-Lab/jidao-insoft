package com.inSoft.pojo.vo;

import com.inSoft.config.UnitInfoConfig;
import com.inSoft.enums.StaticTypeEnum;
import com.inSoft.pojo.SysStatic;
import lombok.Data;

@Data
public class SysStaticVo extends SysStatic {
    //处理枚举信息
    private String typeText;
    //处理单位信息
    private String unitText;

    public SysStaticVo(SysStatic sysStatic) {
        super.setId(sysStatic.getId());
        super.setName(sysStatic.getName());
        super.setValue(sysStatic.getValue());
        super.setType(sysStatic.getType());
        super.setCreatedAt(sysStatic.getCreatedAt());
        super.setUpdatedAt(sysStatic.getUpdatedAt());
        this.typeText = StaticTypeEnum.getByCode(sysStatic.getType()).getValue();
        //根据当前的type参数进行单位处理
        if (sysStatic.getType() == 0||sysStatic.getType() == 5){
            this.unitText = UnitInfoConfig.UNIT_NUMBER_THREE;
        } else if (sysStatic.getType() == 1||sysStatic.getType()==2||sysStatic.getType()==4) {
            this.unitText = UnitInfoConfig.UNIT_NUMBER_FOUR;
        }else if (sysStatic.getType() == 3) {
            this.unitText = UnitInfoConfig.UNIT_NUMBER_TWO;
        }else if (sysStatic.getType() == 6) {
            this.unitText = UnitInfoConfig.UNIT_MONEY_ONE;
        } else {
            this.unitText = UnitInfoConfig.UNIT_NUMBER_ONE;
        }
    }

}