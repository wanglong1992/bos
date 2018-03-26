package cn.itcast.service.impl;

import cn.itcast.dao.AreaRepository;
import cn.itcast.dao.FixedAreaRepository;
import cn.itcast.dao.OrderRepository;
import cn.itcast.domain.base.Area;
import cn.itcast.domain.base.Courier;
import cn.itcast.domain.base.FixedArea;
import cn.itcast.domain.base.SubArea;
import cn.itcast.domain.constants.Constants;
import cn.itcast.domain.take_delivery.Order;
import cn.itcast.domain.take_delivery.WorkBill;
import cn.itcast.service.OrderService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;


@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    @Autowired
    @Qualifier(value = "jmsQueueTemplate")
    private JmsTemplate jmsTemplate;


    @Override
    public void save(Order order) {
        Area sendArea = order.getSendArea();
        Area persistSendArea = areaRepository.findByProvinceAndCityAndDistrict(sendArea.getProvince(), sendArea.getCity(), sendArea.getDistrict());
        // 使用收件人的省市区，查询区域对象，获取收件人区域的持久化对象
        Area recArea = order.getRecArea();
        Area persistRecArea = areaRepository.findByProvinceAndCityAndDistrict(recArea.getProvince(), recArea.getCity(), recArea.getDistrict());
        // 将持久化的对象存放到order
        order.setSendArea(persistSendArea);
        order.setRecArea(persistRecArea);
        String fixedAreaId = WebClient.create(Constants.CRM_MANAGEMENT_URL + "services/customerService/customer/findFixedAreaIdByIdAndAddress/" + order.getCustomer_id() + order.getSendAddress()).accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).get(String.class);
        if (StringUtils.isNotBlank(fixedAreaId)) {
            FixedArea fixedArea = fixedAreaRepository.findOne(fixedAreaId);
            if (fixedArea != null) {
                Set<Courier> couriers = fixedArea.getCouriers();
                while (couriers.iterator().hasNext()) {
                    Courier courier = couriers.iterator().next();
                    //保存订单,同时关联快递员
                    saveOrder(order, courier);
                    //生成工单,同时发送短信给快递员去取件
                    saveWorkBill(order);
                    return;
                }
            }
        }
        for (SubArea subArea : persistSendArea.getSubareas()) {

            if (order.getSendAddress().contains(subArea.getKeyWords()) || order.getSendAddress().contains(subArea.getAssistKeyWords())) {
                Iterator<Courier> iterator = subArea.getFixedArea().getCouriers().iterator();
                while (iterator.hasNext()) {
                    Courier courier = iterator.next();
                    saveOrder(order, courier);
                    saveWorkBill(order);
                    return;
                }
            }

        }


    }

    // 生成工单（发送短信给快递员，快递员负责取件）
    private void saveWorkBill(final Order order) {
        WorkBill workBill = new WorkBill();
        workBill.setType("新");
        /*
         *新单:没有确认货物状态的
         *已通知:自动下单下发短信
         *已确认:接到短信,回复收信确认信息
         *已取件:已经取件成功,发回确认信息 生成工作单
         *已取消:销单
         *
         */
        workBill.setPickstate("新单");
        workBill.setBuildtime(new Date());
        workBill.setAttachbilltimes(0);
        workBill.setRemark(order.getRemark());
        final String smsNumber = RandomStringUtils.randomNumeric(4);
        workBill.setSmsNumber(smsNumber);
        workBill.setCourier(order.getCourier());
        workBill.setOrder(order);
        jmsTemplate.send("bos_smscouries", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                // 创建消息
                // 速运快递 短信序号：${num},取件地址：${sendAddress},联系人：${sendName},手机:${sendMobile}，快递员捎话：${sendMobileMsg}
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("telephone", order.getCourier().getTelephone());
                mapMessage.setString("num", smsNumber);
                mapMessage.setString("sendAddress", order.getSendAddress());
                mapMessage.setString("sendName", order.getSendName());
                mapMessage.setString("sendMobile", order.getSendMobile());
                mapMessage.setString("sendMobileMsg", order.getSendMobileMsg());
                return mapMessage;
            }
        });


    }

    // 保存订单，同时用订单关联快递员（自动分单）
    private void saveOrder(Order order, Courier courier) {
        order.setCourier(courier);
        // 分单类型 1 自动分单 2 人工分单
        order.setOrderType("1");
        orderRepository.save(order);
    }

    @Override
    public Order findByOrderNum(String orderNum) {
        return orderRepository.findByOrderNum(orderNum);
    }
}
