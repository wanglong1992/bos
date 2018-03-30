package cn.itcast.service.impl;

import cn.itcast.dao.SignInfoRepository;
import cn.itcast.dao.TransitInfoRepository;
import cn.itcast.domain.transit.SignInfo;
import cn.itcast.domain.transit.TransitInfo;
import cn.itcast.index.WayBillIndexRepository;
import cn.itcast.service.SignInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignInfoServiceImpl implements SignInfoService {
    @Autowired
    private SignInfoRepository signInfoRepository;
    @Autowired
    private TransitInfoRepository transitInfoRepository;
    @Autowired
    private WayBillIndexRepository wayBillIndexRepository;

    @Override
    public void save(SignInfo signInfo, Integer transitInfoId) {
        // 保存签收录入信息
        signInfoRepository.save(signInfo);
        // 关联运输流程
        TransitInfo transitInfo = transitInfoRepository.findOne(transitInfoId);
        // 更改状态
        if (signInfo.getSignType().equals("正常")) {

            // 正常签收
            transitInfo.setStatus("正常");
            // 更改运单状态（3：表示已签收）
            transitInfo.getWayBill().setSignStatus(3);
            // 更改索引库（只要运单WayBill的数据发生改变，都需要更新索引库）
            wayBillIndexRepository.save(transitInfo.getWayBill());
        } else {
            // 异常
            signInfo.setSignType("异常");
            // 更改运单状态（4：表示异常）
            transitInfo.getWayBill().setSignStatus(4);
            // 更改索引库（只要运单WayBill的数据发生改变，都需要更新索引库）
            wayBillIndexRepository.save(transitInfo.getWayBill());


        }
    }
}
