package com.inSoft.mapper.provider;

import com.inSoft.pojo.WebUsersVipser;
import org.apache.ibatis.jdbc.SQL;

public class WebUsersVipserMapperProvider {

    //更新 uid,status,start_time,end_time,renewals,total_duration,last_renewal_time,income_id
    public String update(WebUsersVipser webUsersVipser) {
        return new SQL(){{
            UPDATE("web_users_vipser");
            if (webUsersVipser.getUid()!=null){
                SET("uid=#{uid}");
            }
            if (webUsersVipser.getStatus()!=null){
                SET("status=#{status}");
            }
            if (webUsersVipser.getStartTime()!=null){
                SET("start_time=#{startTime}");
            }
            if (webUsersVipser.getEndTime()!=null){
                SET("end_time=#{endTime}");
            }
            if (webUsersVipser.getRenewals()!=null){
                SET("renewals=renewals+#{renewals}");
            }
            if (webUsersVipser.getTotalDuration()!=null){
                SET("total_duration=total_duration+#{totalDuration}");
            }
            if (webUsersVipser.getLastRenewalTime()!=null){
                SET("last_renewal_time=#{lastRenewalTime}");
            }
            if (webUsersVipser.getIncomeId()!=null){
                SET("income_id=#{incomeId}");
            }
            WHERE("id=#{id}");
        }}.toString();
    }

}
