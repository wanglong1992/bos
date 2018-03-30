package cn.itcast.service;

import cn.itcast.domain.transit.DeliveryInfo;

public interface DeliveryInfoService {
    void save(DeliveryInfo deliveryInfo, String transitInfoId);
}

