package cn.itcast.service;

import cn.itcast.domain.transit.TransitInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransitInfoService {
    void createTransits(String wayBillIds);

    Page<TransitInfo> findPageQuery(Pageable pageRequest);
}
