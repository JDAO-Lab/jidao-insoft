package com.inSoft.pojo.vo;

import com.inSoft.enums.DownTypeEnum;
import com.inSoft.enums.IsDeletedEnum;
import com.inSoft.pojo.AppVersion;
import lombok.Data;

@Data
public class AppVersionVo extends AppVersion {

    private String isDeletedText;
    private String downTypeText;

    public AppVersionVo(AppVersion appVersion) {
        super.setId(appVersion.getId());
        super.setVersion(appVersion.getVersion());
        super.setTitle(appVersion.getTitle());
        super.setUpLog(appVersion.getUpLog());
        super.setDownUrl(appVersion.getDownUrl());
        super.setDownPwd(appVersion.getDownPwd());
        super.setDownType(appVersion.getDownType());
        super.setCid(appVersion.getCid());
        super.setCreatedAt(appVersion.getCreatedAt());
        super.setUpdatedAt(appVersion.getUpdatedAt());
        super.setIsDeleted(appVersion.getIsDeleted());
        this.isDeletedText = IsDeletedEnum.getByCode(appVersion.getIsDeleted()).getValue();
        this.downTypeText = DownTypeEnum.getByCode(appVersion.getDownType()).getValue();
    }

}
