package com.inSoft.service;

import com.inSoft.pojo.WebCharacteristic;

import java.util.List;

public interface WebCharacteristicService {

    List<WebCharacteristic> listByCondition(WebCharacteristic webCharacteristic);

    boolean save(WebCharacteristic webCharacteristic);

    WebCharacteristic getById(Integer id);

    boolean update(WebCharacteristic webCharacteristic);

    boolean remove(Integer id);

    boolean removeBatch(List<Integer> ids);

    List<WebCharacteristic> pushList();
}
