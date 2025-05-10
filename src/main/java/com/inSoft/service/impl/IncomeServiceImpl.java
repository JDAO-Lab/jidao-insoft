package com.inSoft.service.impl;

import com.inSoft.mapper.IncomeMapper;
import com.inSoft.pojo.Income;
import com.inSoft.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class IncomeServiceImpl implements IncomeService {


    @Autowired
    private IncomeMapper incomeMapper;

    //获取所有数据
    public List<Income> list(){
        return incomeMapper.list();
    }

    //根据条件查询数据
    public List<Income> listByCondition(Income income){
        return incomeMapper.listByCondition(income);
    }

    //保存数据
    public boolean save(Income income){
        return incomeMapper.save(income);
    }

    //根据id获取数据
    public Income getById(Integer id){
        return incomeMapper.getById(id);
    }

    //更新数据
    public boolean update(Income income){
        return incomeMapper.update(income);
    }

    //删除
    public boolean remove(Integer id){
        return incomeMapper.remove(id);
    }

    //批量删除
    public boolean removeBatch(List<Integer> ids){
        return incomeMapper.removeBatch(ids);
    }

    //恢复
    public boolean recovery(Integer id){
        return incomeMapper.recovery(id);
    }

    //批量恢复
    public boolean recoveryBatch(List<Integer> ids){
        return incomeMapper.recoveryBatch(ids);
    }

    @Override
    public List<Income> getPricesToView(Integer num) {
        return incomeMapper.getPricesToView(num);
    }

}
