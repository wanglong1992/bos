package cn.itcast.web.action;

import cn.itcast.domain.transit.DeliveryInfo;
import cn.itcast.service.DeliveryInfoService;
import cn.itcast.web.action.common.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Namespace("/")
@ParentPackage("json-default")
@Controller
@Scope("prototype")
public class DeliveryInfoAction extends BaseAction<DeliveryInfo> {
    private static final long serialVersionUID = -8130157627035776699L;
    @Autowired
    private DeliveryInfoService deliveryInfoService;

    private String transitInfoId;

    public void setTransitInfoId(String transitInfoId) {
        this.transitInfoId = transitInfoId;
    }


    @Action(value = "delivery_save", results = {@Result(name = SUCCESS, type = "redirect", location = "pages/transit/transitinfo.html")})
    public String save() {
        deliveryInfoService.save(model, transitInfoId);
        return SUCCESS;
    }
}
