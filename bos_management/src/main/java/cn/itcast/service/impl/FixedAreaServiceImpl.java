package cn.itcast.service.impl;

import cn.itcast.dao.CourierRepository;
import cn.itcast.dao.FixedAreaRepository;
import cn.itcast.dao.TakeTimeRepository;
import cn.itcast.domain.base.Courier;
import cn.itcast.domain.base.FixedArea;
import cn.itcast.domain.base.TakeTime;
import cn.itcast.service.FixedAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * Created by CC on 2018/3/13.
 */
@Service
public class FixedAreaServiceImpl implements FixedAreaService {
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    @Autowired
    private CourierRepository courierRepository;
    @Autowired
    private TakeTimeRepository takeTimeRepository;

    @Override
    public void save(FixedArea fixedArea) {
        fixedAreaRepository.save(fixedArea);
    }

    @Override
    public Page<FixedArea> findPageQuery(Specification<FixedArea> specification, PageRequest pageable) {
        return fixedAreaRepository.findAll(specification, pageable);
    }

    @Override
    public void associationCourierToFixedArea(String id, Integer courierId, Integer takeTimeId) {
        FixedArea fixedArea = fixedAreaRepository.findOne(id);
        Courier courier = courierRepository.getOne(courierId);
        TakeTime takeTime = takeTimeRepository.getOne(takeTimeId);
        fixedArea.getCouriers().add(courier);
        courier.setTakeTime(takeTime);

    }
}


