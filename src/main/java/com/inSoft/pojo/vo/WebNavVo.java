package com.inSoft.pojo.vo;

import com.inSoft.enums.IsEnableEnum;
import com.inSoft.enums.NavTypeEnum;
import com.inSoft.pojo.WebNav;
import lombok.Data;

@Data
public class WebNavVo extends WebNav {
    //使用状态
    private String enableText;
    private String typeText;

    public WebNavVo(WebNav webNav){
        super.setId(webNav.getId());
        //此处是二级菜单逻辑
        if (webNav.getPid() != null && webNav.getPid() != 0) {
            super.setName("|---" + webNav.getName()); // 给pid不为0或null的name前增加"|---"
        } else {
            super.setName(webNav.getName());
        }
        super.setKeywords(webNav.getKeywords());
        super.setDescription(webNav.getDescription());
        super.setContent(webNav.getContent());
        super.setPath(webNav.getPath());
        super.setType(webNav.getType());
        super.setPid(webNav.getPid());
        super.setSort(webNav.getSort());
        super.setCreatedAt(webNav.getCreatedAt());
        super.setUpdatedAt(webNav.getUpdatedAt());
        super.setEnable(webNav.getEnable());
        super.setCid(webNav.getCid());
        this.enableText = IsEnableEnum.getByCode(webNav.getEnable()).getValue();
        this.typeText = NavTypeEnum.getByCode(webNav.getType()).getValue();
    }
}
