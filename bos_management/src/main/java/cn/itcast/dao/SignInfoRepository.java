package cn.itcast.dao;

import cn.itcast.domain.transit.SignInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignInfoRepository extends JpaRepository<SignInfo, Integer> {
}

