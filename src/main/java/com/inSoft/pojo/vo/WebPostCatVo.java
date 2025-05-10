package com.inSoft.pojo.vo;

import com.inSoft.enums.IsDeletedEnum;
import com.inSoft.pojo.WebPostCat;
import lombok.Data;

@Data
public class WebPostCatVo extends WebPostCat {

    private String isDeletedText;

    public WebPostCatVo(WebPostCat webPostCat){
        this.setId(webPostCat.getId());
        this.setName(webPostCat.getName());
        this.setDescription(webPostCat.getDescription());
        this.setIsDeleted(webPostCat.getIsDeleted());
        this.setCreatedAt(webPostCat.getCreatedAt());
        this.setUpdatedAt(webPostCat.getUpdatedAt());
        this.isDeletedText = IsDeletedEnum.getByCode(webPostCat.getIsDeleted()).getValue();
    }

}
