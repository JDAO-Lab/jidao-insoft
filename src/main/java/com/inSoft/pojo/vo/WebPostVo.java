package com.inSoft.pojo.vo;

import com.inSoft.enums.IsDeletedEnum;
import com.inSoft.pojo.WebPost;
import lombok.Data;

@Data
public class WebPostVo extends WebPost {

    private String isDeletedText;

    public WebPostVo(WebPost webPost) {
        this.setId(webPost.getId());
        this.setTitle(webPost.getTitle());
        this.setDescription(webPost.getDescription());
        this.setContent(webPost.getContent());
        this.setCid(webPost.getCid());
        this.setCreatedAt(webPost.getCreatedAt());
        this.setUpdatedAt(webPost.getUpdatedAt());
        this.setIsDeleted(webPost.getIsDeleted());
        this.setKeywords(webPost.getKeywords());
        this.setViews(webPost.getViews());
        this.setCname(webPost.getCname());
        this.isDeletedText = IsDeletedEnum.getByCode(webPost.getIsDeleted()).getValue();
    }

}
