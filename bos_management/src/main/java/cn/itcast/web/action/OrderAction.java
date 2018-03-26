package cn.itcast.web.action;

import cn.itcast.domain.take_delivery.Order;
import cn.itcast.domain.take_delivery.WayBill;
import cn.itcast.service.OrderService;
import cn.itcast.web.action.common.BaseAction;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@ParentPackage(value = "json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class OrderAction extends BaseAction<Order> {
    private static final long serialVersionUID = 6131713218524404844L;
    @Autowired
    private OrderService orderService;

    //使用订单号查询订单
    @Action(value = "order_findByOrderNum", results = {@Result(name = "success", type = "json")})
    public String findByOrderNum() {
        Order order = orderService.findByOrderNum(model.getOrderNum());
        Map<String,Object> map  = new HashMap<>();
        if(order!=null){
            WayBill wayBill = new WayBill();
            wayBill.setWayBillNum("suyunwaybill"+ RandomStringUtils.randomNumeric(32));
            order.setWayBill(wayBill);
            map.put("success",true);
            map.put("orderData",order);
        }else {
            map.put("success",false);
        }
        pushValueStackToPage(map);
        return SUCCESS;
    }


}
