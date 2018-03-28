package cn.itcast.service.impl;

import cn.itcast.dao.WayBillRepository;
import cn.itcast.domain.take_delivery.WayBill;
import cn.itcast.index.WayBillIndexRepository;
import cn.itcast.service.WayBillService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

@Service
public class WayBillServiceImpl implements WayBillService {
    @Autowired
    private WayBillRepository wayBillRepository;
    @Autowired
    private WayBillIndexRepository wayBillIndexRepository;

    @Override
    public void save(WayBill wayBill) {
        if (wayBill != null && wayBill.getId() != null) {
            WayBill persistWayBill = wayBillRepository.findOne(wayBill.getId());
            if (persistWayBill.getSignStatus() != 1) {
                System.out.println("运单已开始配送,无法保存");
                throw new RuntimeException("运单已开始配送,无法保存");


            }
        }
        /*
         * 运单状态： 1 待发货、 2 派送中、3 已签收、4 异常
         */
        wayBill.setSignStatus(1);
        wayBillIndexRepository.save(wayBill);

        wayBillRepository.save(wayBill);
    }

    @Override
    public Page<WayBill> pageQuery(Pageable pageable, WayBill wayBill) {
        if (StringUtils.isBlank(wayBill.getWayBillNum()) && StringUtils.isBlank(wayBill.getSendAddress())
                && StringUtils.isBlank(wayBill.getRecAddress()) && StringUtils.isBlank(wayBill.getSendProNum()) && (wayBill.getSignStatus() == null || wayBill.getSignStatus() == 0)) {
            return wayBillRepository.findAll(pageable);
        }
        //
        BoolQueryBuilder query = new BoolQueryBuilder();
        // 运单号
        if (StringUtils.isNotBlank(wayBill.getWayBillNum())) {
            QueryBuilder queryBuilder = new TermQueryBuilder("wayBillNum", wayBill.getWayBillNum());
            query.must(queryBuilder);
        }
        // 寄件人地址
        if (StringUtils.isNotBlank(wayBill.getSendAddress())) {
            // 通配符，词条的模糊
            QueryBuilder queryBuilder1 = new WildcardQueryBuilder("sendAddress", "*" + wayBill.getSendAddress() + "*");
            // 先分词，再检索
            QueryBuilder queryBuilder2 = new QueryStringQueryBuilder(wayBill.getSendAddress()).field("sendAddress").defaultOperator(QueryStringQueryBuilder.Operator.AND);// AND表示将查询的结果合并（交集）
            BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
            queryBuilder.should(queryBuilder1).should(queryBuilder2);
            query.must(queryBuilder);
        }
        // 收件人地址
        if (StringUtils.isNotBlank(wayBill.getRecAddress())) {
            QueryBuilder queryBuilder1 = new WildcardQueryBuilder("recAddress", "*" + wayBill.getRecAddress() + "*");
            // 先分词，再检索
            QueryBuilder queryBuilder2 = new QueryStringQueryBuilder(wayBill.getRecAddress()).field("recAddress").defaultOperator(QueryStringQueryBuilder.Operator.AND);
            BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
            queryBuilder.should(queryBuilder1).should(queryBuilder2);
            query.must(queryBuilder);
        }
        //产品类型
        if (StringUtils.isNotBlank(wayBill.getSendProNum())) {
            QueryBuilder queryBuilder = new TermQueryBuilder("sendProNum", wayBill.getSendProNum());
            query.must(queryBuilder);
        }
        if (wayBill.getSignStatus() != null && wayBill.getSignStatus() > 0) {
            QueryBuilder queryBuilder = new TermQueryBuilder("signStatus", wayBill.getSignStatus());
            query.must(queryBuilder);
        }
        SearchQuery searchQuery = new NativeSearchQuery(query);

        return wayBillIndexRepository.search(searchQuery);
    }

    @Override
    public WayBill findByWayBillNum(String wayBillNum) {
        return wayBillRepository.findOne(Integer.valueOf(wayBillNum));
    }
}
