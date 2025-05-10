package com.inSoft.mapper;

import com.inSoft.mapper.provider.WebUsersVipserMapperProvider;
import com.inSoft.pojo.WebUsersVipser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WebUsersVipserMapper {

    //根据uid获取会员信息
    @Select("select v.*,i.name as incomeText from web_users_vipser as v  left join web_income as i on i.id=v.income_id  where v.uid=#{uid} ")
    WebUsersVipser getByUid(@Param("uid") Integer uid);

    //保存 insert
    @Insert("insert into web_users_vipser(uid,status,start_time,end_time,renewals,total_duration,last_renewal_time,income_id) values(#{uid},#{status},#{startTime},#{endTime},#{renewals},#{totalDuration},#{lastRenewalTime},#{incomeId})")
    boolean save(WebUsersVipser newWebUsersVipser);

    //更新
    @UpdateProvider(type= WebUsersVipserMapperProvider.class,method="update")
    boolean update(WebUsersVipser webUsersVipser);
}
