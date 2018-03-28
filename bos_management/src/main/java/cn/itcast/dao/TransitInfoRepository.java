package cn.itcast.dao;

import cn.itcast.domain.transit.TransitInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransitInfoRepository extends JpaRepository<TransitInfo, Integer>, JpaSpecificationExecutor<TransitInfo> {

}
