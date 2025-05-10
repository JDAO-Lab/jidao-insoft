package com.inSoft.service.impl;

import com.inSoft.mapper.WebPostMapper;
import com.inSoft.pojo.WebPost;
import com.inSoft.service.WebPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebPostServiceImpl implements WebPostService {

    @Autowired
    private WebPostMapper webPostMapper;

    //根据条件查询
    public List<WebPost> listByCondition(WebPost webPost) {
        return webPostMapper.listByCondition(webPost);
    }

    //保存数据
    public boolean save(WebPost webPost) {
        return webPostMapper.save(webPost);
    }

    //根据id获取数据
    public WebPost getById(Integer id) {
        return webPostMapper.getById(id);
    }

    //更新数据
    public boolean update(WebPost webPost) {
        return webPostMapper.update(webPost);
    }

    //删除
    public boolean remove(Integer id) {
        return webPostMapper.remove(id);
    }

    //批量删除
    public boolean removeBatch(List<Integer> ids) {
        return webPostMapper.removeBatch(ids);
    }

    //彻底删除
    public boolean removed(Integer id) {
        return webPostMapper.removed(id);
    }

    //恢复
    public boolean recovery(Integer id) {
        return webPostMapper.recovery(id);
    }

    //批量恢复
    public boolean recoveryBatch(List<Integer> ids) {
        return webPostMapper.recoveryBatch(ids);
    }

    //阅读增加
    public boolean views(Integer id) {
        return webPostMapper.views(id);
    }

    @Override
    public List<WebPost> getListByCid(Integer webPostCatId, int num) {
        return webPostMapper.getListByCid(webPostCatId, num);
    }

    @Override
    public List<WebPost> listBykeyword(String keyword, int num) {
        return webPostMapper.listBykeyword(keyword, num);
    }

}
