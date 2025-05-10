package com.inSoft.service.impl;

import com.inSoft.mapper.WebPushProductMapper;
import com.inSoft.pojo.WebPushProduct;
import com.inSoft.service.WebPushProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebPushProductServiceImpl implements WebPushProductService {

    @Autowired
    private WebPushProductMapper webPushProductMapper;

    @Override
    public List<WebPushProduct> listByCondition(WebPushProduct webPushProduct) {
        return webPushProductMapper.listByCondition(webPushProduct);
    }

    @Override
    public boolean save(WebPushProduct webPushProduct) {
        return webPushProductMapper.save(webPushProduct);
    }

    @Override
    public WebPushProduct getById(Integer id) {
        return webPushProductMapper.getById(id);
    }

    @Override
    public boolean update(WebPushProduct webPushProduct) {
        return webPushProductMapper.update(webPushProduct);
    }

    @Override
    public boolean remove(Integer id) {
        return webPushProductMapper.remove(id);
    }

    @Override
    public boolean removeBatch(List<Integer> ids) {
        return webPushProductMapper.removeBatch(ids);
    }

    @Override
    public List<WebPushProduct> pushList() {
        return webPushProductMapper.pushList();
    }
}
