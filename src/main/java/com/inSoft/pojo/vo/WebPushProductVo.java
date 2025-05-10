package com.inSoft.pojo.vo;

import com.inSoft.pojo.WebPushProduct;
import lombok.Data;

@Data
public class WebPushProductVo extends WebPushProduct {

    public WebPushProductVo(WebPushProduct webPushProduct){
        super.setId(webPushProduct.getId());
        super.setName(webPushProduct.getName());
        super.setLogo(webPushProduct.getLogo());
        super.setHref(webPushProduct.getHref());
        super.setSort(webPushProduct.getSort());
        super.setCreatedAt(webPushProduct.getCreatedAt());
        super.setUpdatedAt(webPushProduct.getUpdatedAt());
    }
}
