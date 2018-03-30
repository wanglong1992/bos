package cn.itcast.service;

import cn.itcast.domain.transit.SignInfo;

public interface SignInfoService {
    void save(SignInfo signInfo, Integer transitInfoId);
}
