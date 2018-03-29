package cn.itcast.service.impl;

import cn.itcast.dao.TransitInfoRepository;
import cn.itcast.dao.WayBillRepository;
import cn.itcast.domain.take_delivery.WayBill;
import cn.itcast.domain.transit.TransitInfo;
import cn.itcast.index.WayBillIndexRepository;
import cn.itcast.service.TransitInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransitInfoServiceImpl implements TransitInfoService {


    @Autowired
    private TransitInfoRepository transitInfoRepository;
    @Autowired
    private WayBillRepository wayBillRepository;
    @Autowired
    private WayBillIndexRepository wayBillIndexRepository;


    @Override
    public void createTransits(String wayBillIds) {
        if (StringUtils.isNotBlank(wayBillIds)) {
            String[] wayBillId = wayBillIds.split(",");
            for (String wayBillIdd : wayBillId) {
                WayBill wayBill = wayBillRepository.findOne(Integer.parseInt(wayBillIdd));
                if (wayBill.getSignStatus() == 1) {
                    TransitInfo transitInfo = new TransitInfo();
                    transitInfo.setWayBill(wayBill);
                    transitInfo.setStatus("出入库中转");
                    transitInfoRepository.save(transitInfo);
                    wayBill.setSignStatus(2);
                    wayBillIndexRepository.save(wayBill);
                }
            }
        }
    }

    @Override
    public Page<TransitInfo> findPageQuery(Pageable pageRequest) {
        return transitInfoRepository.findAll(pageRequest);
    }
}
