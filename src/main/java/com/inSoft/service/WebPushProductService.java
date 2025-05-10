package com.inSoft.service;

import com.inSoft.pojo.WebPushProduct;

import java.util.List;

public interface WebPushProductService {
    List<WebPushProduct> listByCondition(WebPushProduct webPushProduct);

    boolean save(WebPushProduct webPushProduct);

    WebPushProduct getById(Integer id);

    boolean update(WebPushProduct webPushProduct);

    boolean remove(Integer id);

    boolean removeBatch(List<Integer> ids);

    List<WebPushProduct> pushList();
}
