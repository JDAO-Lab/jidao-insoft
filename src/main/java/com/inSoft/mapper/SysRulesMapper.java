package com.inSoft.mapper;

import com.inSoft.mapper.provider.SysRulesProvider;
import com.inSoft.pojo.SysRules;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysRulesMapper {

    @Select("select rules from sys_rules where is_deleted=0 and id=#{id}")
     String getRulesById(Integer id);

    @Select("SELECT * FROM sys_rules WHERE id=#{id}")
    SysRules getById(Integer id);

    @Select("SELECT * FROM sys_rules")
    List<SysRules> list();

    @SelectProvider(type = SysRulesProvider.class, method = "listByCondition")
    List<SysRules> listByCondition(SysRules sysRules);

    @Select("SELECT id,name FROM sys_rules where is_deleted=0")
    List<SysRules> selectRules();

    @Insert("INSERT INTO sys_rules(name, rules, is_deleted,description, created_at) VALUES(#{name}, #{rules}, 0,#{description}, #{createdAt})")
    boolean save(SysRules sysRules);

    @UpdateProvider(type = SysRulesProvider.class, method = "update")
    boolean update(SysRules sysRules);

    @Update("UPDATE sys_rules SET is_deleted=1 WHERE id=#{id}")
    boolean remove(Integer id);

    @UpdateProvider(type = SysRulesProvider.class, method = "removeBatch")
    boolean removeBatch(List<Integer> ids);

    @Update("UPDATE sys_rules SET is_deleted=0 WHERE id=#{id}")
    boolean recovery(Integer id);

    @UpdateProvider(type = SysRulesProvider.class, method = "recoveryBatch")
    boolean recoveryBatch(List<Integer> ids);

}
