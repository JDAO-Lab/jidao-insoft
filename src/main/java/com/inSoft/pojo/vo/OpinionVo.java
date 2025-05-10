package com.inSoft.pojo.vo;

import com.inSoft.enums.IsDeletedEnum;
import com.inSoft.pojo.Opinion;
import lombok.Data;

@Data
public class OpinionVo extends Opinion {

    private String isDeletedText;

    public OpinionVo(Opinion opinion){
        super.setId(opinion.getId());
        super.setTitle(opinion.getTitle());
        super.setContent(opinion.getContent());
        super.setCreatedAt(opinion.getCreatedAt());
        super.setUpdatedAt(opinion.getUpdatedAt());
        super.setIsDeleted(opinion.getIsDeleted());
        super.setUrl(opinion.getUrl());
        super.setIp(opinion.getIp());
        //输出枚举信息
        this.isDeletedText = IsDeletedEnum.getByCode(opinion.getIsDeleted()).getValue();
    }

}
