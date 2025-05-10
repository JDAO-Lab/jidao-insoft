package com.inSoft.mapper;

import com.inSoft.mapper.provider.WebNavProvider;
import com.inSoft.pojo.WebNav;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WebNavMapper {

    //根据条件查询数据
    @SelectProvider(type = WebNavProvider.class, method = "listByCondition")
    List<WebNav> listByCondition(WebNav webNav);

    //统计所有数据
    @Select("SELECT IFNULL((select count(id) from web_nav where pid=#{id}),0)")
    Integer countByPid(Integer id);

    //统计总数
    @SelectProvider(type = WebNavProvider.class, method = "countByPids")
    Integer countByPids(List<Integer> ids);

    //获取顶级菜单数据
    @Select("select * from web_nav where pid=0 and cid=#{cid}")
    List<WebNav> getTopMenu(Integer cid);

    //根据id获取数据
    @Select("select * from web_nav where id=#{id}")
    WebNav getById(Integer id);

    //保存数据
    @Insert("insert into web_nav(name,keywords,description,content,enable,path,pid,sort,type,created_at,cid) values(#{name},#{keywords},#{description},#{content},#{enable},#{path},#{pid},#{sort},#{type},#{createdAt},#{cid})")
    boolean save(WebNav webNav);

    //删除
    @Delete("DELETE FROM web_nav WHERE id = #{id}")
    boolean remove(@Param("id") Integer id);

    //批量删除
    @DeleteProvider(type = WebNavProvider.class, method = "removeBatch")
    boolean removeBatch(List<Integer> ids);

    //更新
    @UpdateProvider(type = WebNavProvider.class, method = "update")
    boolean update(WebNav webNav);

    @Select("select * from web_nav where path=#{path}")
    WebNav getByPath(String path);

    //获取前台导航信息根据cid
    @Select("select id,name,path,pid,type from web_nav where cid=#{cid} and enable=1 order by sort asc")
    List<WebNav> getNavDataByCid(Integer cid);
}
