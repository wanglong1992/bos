package cn.itcast.service;

import cn.itcast.domain.take_delivery.WayBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WayBillService {
    void save(WayBill wayBill);

    Page<WayBill> pageQuery(Pageable pageable);

}
