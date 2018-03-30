package cn.itcast.service.impl;

import cn.itcast.dao.DeliveryInfoRepository;
import cn.itcast.dao.TransitInfoRepository;
import cn.itcast.domain.transit.DeliveryInfo;
import cn.itcast.domain.transit.TransitInfo;
import cn.itcast.service.DeliveryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryInfoServiceImpl implements DeliveryInfoService {
    @Autowired
    private TransitInfoRepository transitInfoRepository;
    @Autowired
    private DeliveryInfoRepository deliveryInfoRepository;


    @Override
    public void save(DeliveryInfo deliveryInfo, String transitInfoId) {
        // 保存开始配送信息
        deliveryInfoRepository.save(deliveryInfo);
        // 查询运输配送对象
        TransitInfo transitInfo = transitInfoRepository.findOne(Integer.valueOf(transitInfoId));
        transitInfo.setDeliveryInfo(deliveryInfo);
        transitInfo.setStatus("开始配送");
    }
}
