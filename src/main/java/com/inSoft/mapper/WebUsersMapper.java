package com.inSoft.mapper;

import com.inSoft.mapper.provider.WebUsersProvider;
import com.inSoft.pojo.WebUsers;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WebUsersMapper {

    //查询所有数据
    @Select("select * from web_users")
    List<WebUsers> list();

    //根据id获取详情数据
    @Select("select * from web_users where id=#{id}")
    WebUsers getById(@Param("id") Integer id);

    //根据条件查询数据
    @SelectProvider(type = WebUsersProvider.class, method = "listByCondition")
    List<WebUsers> listByCondition(WebUsers webUsers);

    //更新数据
    @UpdateProvider(type = WebUsersProvider.class, method = "update")
    boolean update(WebUsers webUsers);

    //批量禁用
    @UpdateProvider(type = WebUsersProvider.class, method = "disableBatch")
    boolean disableBatch(List<Integer> ids);

    //批量启用
    @UpdateProvider(type = WebUsersProvider.class, method = "enableBatch")
    boolean enableBatch(List<Integer> ids);

    //根据token获取数据
    @Select("select * from web_users where token=#{token} and enable=1")
    WebUsers getUserInfoByToken(String token);

    //注册
    @Insert("insert into web_users(phone, nickname, username, token, email, avatar, password, enable, login_count, created_at, sex, ip, address,login_at,updated_at) " +
            "values(#{phone}, #{nickname}, #{username}, #{token}, #{email}, #{avatar}, #{password}, #{enable}, #{loginCount}, #{createdAt}, #{sex}, #{ip}, #{address},#{loginAt},#{updatedAt})")
    boolean reg(WebUsers webUsers);

    //查询是否存在相同手机号
    @Select("select count(id) from web_users where phone=#{phone}")
    boolean isOnlyPhone(String phone);

    //根据手机号查询用户信息
    @Select("select * from web_users where phone=#{phone}")
    WebUsers getUserInfoByPhone(String phone);

    //判断是否在线
    @Select("select count(id) from web_users where token=#{userToken}")
    boolean isOnlineIn(String userToken);

    //更新密码
    @Update("update web_users set password=#{password} where phone=#{phone}")
    boolean updatePassword(@Param("phone") String phone,@Param("password") String password);

    //更新token
    @Update("update web_users set token=#{token} where id=#{id}")
    boolean updateToken(@Param("id") int id,@Param("token") String token);
}
