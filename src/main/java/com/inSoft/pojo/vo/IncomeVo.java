package com.inSoft.pojo.vo;

import com.inSoft.enums.IsDeletedEnum;
import com.inSoft.pojo.Income;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class IncomeVo extends Income{

    private String isDeletedText;
    private List<Map<String, Object>> privilegeList = new ArrayList<Map<String, Object>>();

    public IncomeVo(Income income){
        super.setId(income.getId());
        super.setCreatedAt(income.getCreatedAt());
        super.setUpdatedAt(income.getUpdatedAt());
        super.setIsDeleted(income.getIsDeleted());
        super.setPrice(income.getPrice());
        super.setDescription(income.getDescription());
        super.setDiscount(income.getDiscount());
        super.setDuration(income.getDuration());
        super.setName(income.getName());
        super.setIcon(income.getIcon());
        super.setPrivilege(income.getPrivilege());
        super.setSort(income.getSort());
        //判断privilege是否存在
        if (income.getPrivilege() != null && !income.getPrivilege().isEmpty()) {
            // 将 getPrivilege 转为数组
            String[] privileges = income.getPrivilege().split(",");
            for (String privilege : privileges) {
                // 分割 privilege 按:号
                String[] split = privilege.split(":");
                // 输出 map 对象 0 和 1
                Map<String, Object> map = new HashMap<>();
                map.put("name", split[0]); // 服务特权项
                map.put("allow", split[1]); // 0代表 不允许  1代表允许
                this.privilegeList.add(map);
            }
        }
        this.isDeletedText = IsDeletedEnum.getByCode(income.getIsDeleted()).getValue();
    }
}
