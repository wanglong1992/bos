package cn.itcast.service.impl;

import cn.itcast.dao.InOutStorageInfoRepository;
import cn.itcast.dao.TransitInfoRepository;
import cn.itcast.domain.transit.InOutStorageInfo;
import cn.itcast.domain.transit.TransitInfo;
import cn.itcast.service.InOutStorageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InOutStorageInfoServiceImpl implements InOutStorageInfoService {
    @Autowired
    private InOutStorageInfoRepository inOutStorageInfoRepository;
    @Autowired
    private TransitInfoRepository transitInfoRepository;


    @Override
    public void save(InOutStorageInfo inOutStorageInfo, Integer transitInfoId) {
        // 保存出入库信息
        inOutStorageInfoRepository.save(inOutStorageInfo);
        // 查询TransitInfo的运输信息
        TransitInfo transitInfo = transitInfoRepository.findOne(transitInfoId);
        // 关联出入库信息 到 运输配送对象，生成数据列的集合索引，@OrderColumn(name = "C_IN_OUT_INDEX")
        transitInfo.getInOutStorageInfos().add(inOutStorageInfo);
        // 修改状态
        if (inOutStorageInfo.getOperation().equals("到达网点")) {
            // 设置运输状态为到达网点
            transitInfo.setStatus("到达网点");
            // 更新网点地址 ，显示配送路径
            transitInfo.setOutletAddress(inOutStorageInfo.getAddress());
        }

    }
}
