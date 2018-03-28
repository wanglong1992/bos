package cn.itcast.web.action;

import cn.itcast.domain.take_delivery.WayBill;
import cn.itcast.service.WayBillService;
import cn.itcast.web.action.common.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@Scope(value = "prototype")
@ParentPackage("json-default")
@Namespace("/")
public class WayBillAction extends BaseAction<WayBill> {
    private static final long serialVersionUID = 6268976796492782151L;
    private static final Logger LOGGER = LoggerFactory.getLogger(WayBillAction.class);
    @Autowired
    private WayBillService wayBillService;

    @Action(value = "waybill_save", results = {@Result(name = "success", type = "json")})
    public String save() {
        Map<String, Object> map = new HashMap<>();

        try {
            if (model.getOrder() != null && model.getOrder().getId() != null) {
                model.setOrder(null);
            }
            wayBillService.save(model);
            map.put("success", true);
            map.put("msg", "保存运单成功,运单号是" + model.getWayBillNum());
            LOGGER.info("保存运单成功，运单号是：" + model.getWayBillNum() + new Date());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", "保存运单失败,运单号是" + model.getWayBillNum() + new Date());
            LOGGER.error("保存运单失败，运单号是：" + model.getWayBillNum() + new Date());
        }

        pushValueStackToPage(map);
        return SUCCESS;
    }

    @Action(value = "waybill_page", results = {@Result(name = "success", type = "json")})
    public String pageQuery() {
        Pageable pageable = new PageRequest(page - 1, rows, new Sort(Sort.Direction.ASC, "id"));
        Page<WayBill> page = wayBillService.pageQuery(pageable, model);
        pushValueStackToPage(page);
        return SUCCESS;
    }

    @Action(value = "load_waybill_by_ordernum", results = {@Result(name = "success", type = "json")})
    public String getWaybillByOrderNum() {
        // System.out.println(model.getOrder().getOrderNum());

        Map<String, Object> map = new HashMap<>();
        WayBill wayBill = wayBillService.findByWayBillNum(model.getWayBillNum());
        if (wayBill == null) {
            map.put("success", false);
        } else {
            map.put("success", true);
            //map.put()
        }

        return SUCCESS;
    }
}
