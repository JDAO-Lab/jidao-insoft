package com.inSoft.pojo.vo;

import com.inSoft.pojo.WebCharacteristic;
import lombok.Data;

@Data
public class WebCharacteristicVo extends WebCharacteristic {

    public WebCharacteristicVo(WebCharacteristic webCharacteristic){
        super.setId(webCharacteristic.getId());
        super.setTitle(webCharacteristic.getTitle());
        super.setThumb(webCharacteristic.getThumb());
        super.setDescription(webCharacteristic.getDescription());
        super.setShortDescription(webCharacteristic.getShortDescription());
        super.setSort(webCharacteristic.getSort());
        super.setCreatedAt(webCharacteristic.getCreatedAt());
        super.setUpdatedAt(webCharacteristic.getUpdatedAt());
    }

}
