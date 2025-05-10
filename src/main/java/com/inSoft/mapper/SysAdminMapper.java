package com.inSoft.mapper;

import com.inSoft.mapper.provider.SysAdminProvider;
import com.inSoft.pojo.SysAdmin;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysAdminMapper {

    @Select("select * from sys_admin")
    List<SysAdmin> list();

    @SelectProvider(type = SysAdminProvider.class, method = "listByCondition")
    List<SysAdmin> listByCondition(SysAdmin sysAdmin);

    @Insert("insert into sys_admin(username, password, token, created_at, updated_at, is_deleted, rule_id,avatar,enable) values(#{username}, #{password}, #{token}, #{createdAt}, #{updatedAt},0, #{ruleId},#{avatar},#{enable})")
    boolean save(SysAdmin sysAdmin);

    //更新数据
    @UpdateProvider(type = SysAdminProvider.class, method = "update")
    boolean update(SysAdmin sysAdmin);

    //登录后更新token及更新时间
    @Update("update sys_admin set  token=#{token},login_at=now() where id=#{id}")
    void loginUpDate(@Param("id") Integer id,@Param("token") String token);

    //根据id删除数据
    @Delete("delete from sys_admin where id=#{id}")
    void delete(Integer id);

    //条件查询1
    @Select("select * from sys_admin where id=#{id}")
    SysAdmin getById(Integer id);

    //根据密码和用户名进行查询返回用户数据用于登录 传递参数，返回对象数据
    @Select("select * from sys_admin where username=#{username} and password=#{password}")
    SysAdmin loginAuthSql(@Param("username") String username,@Param("password") String password);

    @Select("select count(*) from sys_admin where token=#{token} and is_deleted=0")
    int countUserByToken(String token);

    @Select("select * from sys_admin where token=#{token}")
    SysAdmin getUserInfoByToken(String token);

    @Select("select rule_id from sys_admin where token=#{token}")
    int getRuleIdByToken(String token);

    @Select("select count(*) from sys_admin where username=#{username}")
    int isOnlyUsername(String username);

    @Update("UPDATE sys_admin SET is_deleted=1 WHERE id=#{id}")
    boolean remove(Integer id);

    @UpdateProvider(type = SysAdminProvider.class, method = "removeBatch")
    boolean removeBatch(List<Integer> ids);

}