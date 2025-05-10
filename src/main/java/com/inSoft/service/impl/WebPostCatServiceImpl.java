package com.inSoft.service.impl;

import com.inSoft.mapper.WebPostCatMapper;
import com.inSoft.pojo.WebPostCat;
import com.inSoft.service.WebPostCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebPostCatServiceImpl implements WebPostCatService {

    @Autowired
    private WebPostCatMapper webPostCatMapper;

    //根据条件查询
    public List<WebPostCat> listByCondition(WebPostCat webPostCat) {
        return webPostCatMapper.listByCondition(webPostCat);
    }

    //保存数据
    public boolean save(WebPostCat webPostCat) {
        return webPostCatMapper.save(webPostCat);
    }

    //根据id获取数据
    public WebPostCat getById(Integer id) {
        return webPostCatMapper.getById(id);
    }

    //更新数据
    public boolean update(WebPostCat webPostCat) {
        return webPostCatMapper.update(webPostCat);
    }

    //删除
    public boolean remove(Integer id) {
        return webPostCatMapper.remove(id);
    }

    //批量删除
    public boolean removeBatch(List<Integer> ids) {
        return webPostCatMapper.removeBatch(ids);
    }

    //恢复
    public boolean recovery(Integer id) {
        return webPostCatMapper.recovery(id);
    }

    //批量恢复
    public boolean recoveryBatch(List<Integer> ids) {
        return webPostCatMapper.recoveryBatch(ids);
    }

    //select
    public List<WebPostCat> getSelect() {
        return webPostCatMapper.getSelect();
    }

}
