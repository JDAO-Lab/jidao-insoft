package com.inSoft.mapper;

import com.inSoft.mapper.provider.SysRulesMenuProvider;
import com.inSoft.pojo.SysRulesMenu;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysRulesMenuMapper {

    @Select("SELECT * FROM sys_rules_menu WHERE is_deleted=0 order by sort asc")
    List<SysRulesMenu> list();

    @Select("SELECT * FROM sys_rules_menu WHERE is_deleted=0 AND id=#{id}")
    SysRulesMenu getById(Integer id);

    //根据条件进行查询关联数据
    @Select("SELECT id,pid,title FROM sys_rules_menu WHERE is_deleted=0 order by sort asc")
    List<SysRulesMenu> getSelect(SysRulesMenu sysRulesMenu);

    @Select("select id, title, href, pid, icon, target from sys_rules_menu where id in(${ids}) and is_deleted = 0 and hide = 0 and enable=1 order by sort asc")
    List<SysRulesMenu> getUserRulesByIds(String ids);

    @Select("select id, title, href, pid, icon, target, description from sys_rules_menu where href=#{href} and is_deleted=0")
    SysRulesMenu getMenuByHref(String href);

    @Select("select id, title, href, pid, icon, target from sys_rules_menu where pid != 0 and is_deleted = 0 order by sort asc")
    List<SysRulesMenu> getUserRulesMenuSon();

    @Select("select id from sys_rules_menu where href=#{href}")
    int getIdByHref(String href);

    @Insert("INSERT INTO sys_rules_menu (title, href, pid, icon, target,description,hide,allow,sort,enable,created_at) VALUES" +
            " (#{title}, #{href}, #{pid}, #{icon}, #{target},#{description},#{hide},#{allow},#{sort},#{enable},#{createdAt})")
    boolean save(SysRulesMenu sysRulesMenu);

    @Update("UPDATE sys_rules_menu SET is_deleted = 1 WHERE id = #{id}")
    boolean remove(Integer id);

    @UpdateProvider(type = SysRulesMenuProvider.class, method = "update")
    boolean update(SysRulesMenu sysRulesMenu);

    @Select("select count(id) from sys_rules_menu where pid=#{pid} and is_deleted=0")
    int getSonByPid(Integer pid);

    @SelectProvider(type = SysRulesMenuProvider.class, method = "getSonCountByPids")
    int getSonByPidBatch(List<Integer> ids);

    @UpdateProvider(type = SysRulesMenuProvider.class, method = "removeBatch")
    boolean removeBatch(List<Integer> ids);


}