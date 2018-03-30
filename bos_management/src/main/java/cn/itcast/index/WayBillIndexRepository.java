package cn.itcast.index;

import cn.itcast.domain.take_delivery.WayBill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface WayBillIndexRepository extends ElasticsearchRepository<WayBill, Integer> {

    List<WayBill> findByWayBillNum(String wayBillNum);
}
