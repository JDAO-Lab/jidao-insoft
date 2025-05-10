package com.inSoft.pojo.vo;

import com.inSoft.enums.IsEnableEnum;
import com.inSoft.enums.SexEnum;
import com.inSoft.pojo.WebUsers;
import lombok.Data;

@Data
public class WebUsersVo extends WebUsers {

    private String sexText;
    private String enableText;

    public WebUsersVo(WebUsers webUsers){
        super.setId(webUsers.getId());
        super.setUsername(webUsers.getUsername());
        super.setPassword(webUsers.getPassword());
        super.setNickname(webUsers.getNickname());
        super.setEmail(webUsers.getEmail());
        super.setPhone(webUsers.getPhone());
        super.setAvatar(webUsers.getAvatar());
        super.setSex(webUsers.getSex());
        super.setIp(webUsers.getIp());
        super.setCreatedAt(webUsers.getCreatedAt());
        super.setUpdatedAt(webUsers.getUpdatedAt());
        super.setLoginAt(webUsers.getLoginAt());
        super.setEnable(webUsers.getEnable());
        super.setLoginCount(webUsers.getLoginCount());
        super.setAddress(webUsers.getAddress());
        super.setToken(webUsers.getToken());
        //输出枚举
        this.sexText = SexEnum.getByCode(webUsers.getSex()).getValue();
        this.enableText = IsEnableEnum.getByCode(webUsers.getEnable()).getValue();
    }

}
