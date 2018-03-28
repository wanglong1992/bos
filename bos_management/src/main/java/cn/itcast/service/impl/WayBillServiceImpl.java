package cn.itcast.service.impl;

import cn.itcast.dao.WayBillRepository;
import cn.itcast.domain.take_delivery.WayBill;
import cn.itcast.index.WayBillIndexRepository;
import cn.itcast.service.WayBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WayBillServiceImpl implements WayBillService {
    @Autowired
    private WayBillRepository wayBillRepository;
    @Autowired
    private WayBillIndexRepository wayBillIndexRepository;

    @Override
    public void save(WayBill wayBill) {
        wayBillRepository.save(wayBill);
    }

    @Override
    public Page<WayBill> pageQuery(Pageable pageable, WayBill wayBill) {

        return wayBillRepository.findAll(pageable);
    }

    @Override
    public WayBill findByWayBillNum(String wayBillNum) {
        return wayBillRepository.findOne(Integer.valueOf(wayBillNum));
    }
}
