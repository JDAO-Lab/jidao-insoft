package com.inSoft.pojo.vo;

import com.inSoft.enums.IsDeletedEnum;
import com.inSoft.pojo.WebNavCat;
import lombok.Data;

@Data
public class WebNavCatVo extends WebNavCat {
    private String isDeletedText;

    public WebNavCatVo(WebNavCat webNavCat){
        super.setId(webNavCat.getId());
        super.setName(webNavCat.getName());
        super.setDescription(webNavCat.getDescription());
        super.setIsDeleted(webNavCat.getIsDeleted());
        super.setCreatedAt(webNavCat.getCreatedAt());
        super.setUpdatedAt(webNavCat.getUpdatedAt());
        this.isDeletedText = IsDeletedEnum.getByCode(webNavCat.getIsDeleted()).getValue();
    }
}
