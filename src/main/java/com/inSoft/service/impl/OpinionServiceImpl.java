package com.inSoft.service.impl;

import com.inSoft.mapper.OpinionMapper;
import com.inSoft.pojo.Opinion;
import com.inSoft.service.OpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpinionServiceImpl implements OpinionService {

    @Autowired
    private OpinionMapper opinionMapper;

    //更新数据
    public boolean update(Opinion opinion){
        return opinionMapper.update(opinion);
    }

    //根据条件查询数据
    public List<Opinion> listByCondition(Opinion opinion){
        return opinionMapper.listByCondition(opinion);
    }

    //根据id获取数据
    public Opinion getById(Integer id){
        return opinionMapper.getById(id);
    }

    //批量删除
    public boolean removeBatch(List<Integer> ids){
        return opinionMapper.removeBatch(ids);
    }

    @Override
    public boolean save(Opinion opinion) {
        return opinionMapper.save(opinion);
    }

}
