package cn.itcast.dao;

import cn.itcast.domain.transit.InOutStorageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InOutStorageInfoRepository extends JpaRepository<InOutStorageInfo, Integer>, JpaSpecificationExecutor<InOutStorageInfo> {
}
