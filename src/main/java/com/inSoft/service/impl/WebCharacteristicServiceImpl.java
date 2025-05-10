package com.inSoft.service.impl;

import com.inSoft.mapper.WebCharacteristicMapper;
import com.inSoft.pojo.WebCharacteristic;
import com.inSoft.service.WebCharacteristicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebCharacteristicServiceImpl implements WebCharacteristicService {

    @Autowired
    private WebCharacteristicMapper webCharacteristicMapper;


    @Override
    public List<WebCharacteristic> listByCondition(WebCharacteristic webCharacteristic) {
        return webCharacteristicMapper.listByCondition(webCharacteristic);
    }

    @Override
    public boolean save(WebCharacteristic webCharacteristic) {
        return webCharacteristicMapper.save(webCharacteristic);
    }

    @Override
    public WebCharacteristic getById(Integer id) {
        return webCharacteristicMapper.getById(id);
    }

    @Override
    public boolean update(WebCharacteristic webCharacteristic) {
        return webCharacteristicMapper.update(webCharacteristic);
    }

    @Override
    public boolean remove(Integer id) {
        return webCharacteristicMapper.remove(id);
    }

    @Override
    public boolean removeBatch(List<Integer> ids) {
        return webCharacteristicMapper.removeBatch(ids);
    }

    @Override
    public List<WebCharacteristic> pushList() {
        return webCharacteristicMapper.pushList();
    }
}
