package com.inSoft.pojo.vo;

import com.inSoft.enums.IsDeletedEnum;
import com.inSoft.enums.IsEnableEnum;
import com.inSoft.pojo.SysAdmin;
import lombok.Data;

@Data
public class SysAdminVo extends SysAdmin{

    private String enableText;
    private String isDeletedText;

    public SysAdminVo(SysAdmin sysAdmin){
        super.setId(sysAdmin.getId());
        super.setUsername(sysAdmin.getUsername());
        super.setAvatar(sysAdmin.getAvatar());
        super.setEnable(sysAdmin.getEnable());
        super.setPassword(sysAdmin.getPassword());
        super.setIsDeleted(sysAdmin.getIsDeleted());
        super.setToken(sysAdmin.getToken());
        super.setRuleId(sysAdmin.getRuleId());
        super.setCreatedAt(sysAdmin.getCreatedAt());
        super.setUpdatedAt(sysAdmin.getUpdatedAt());
        super.setLoginAt(sysAdmin.getLoginAt());
        super.setRuleName(sysAdmin.getRuleName());
        this.isDeletedText = IsDeletedEnum.getByCode(sysAdmin.getIsDeleted()).getValue();
        this.enableText = IsEnableEnum.getByCode(sysAdmin.getEnable()).getValue();
    }

}
