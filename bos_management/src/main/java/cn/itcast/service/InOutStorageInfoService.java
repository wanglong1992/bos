package cn.itcast.service;

import cn.itcast.domain.transit.InOutStorageInfo;

public interface InOutStorageInfoService {
    void save(InOutStorageInfo inOutStorageInfo, Integer transitInfoId);
}
