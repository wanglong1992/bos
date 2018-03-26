package cn.itcast.bos.web.action;

import cn.itcast.bos.web.action.common.BaseAction;
import cn.itcast.domain.base.Area;
import cn.itcast.domain.constants.Constants;
import cn.itcast.domain.take_delivery.Order;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/")
public class OrderAction extends BaseAction<Order> {
    private static final long serialVersionUID = 0xdf5ab5ee38f64fd7L;
    private String sendAreaInfo;
    private String recAreaInfo;
    

	public void setSendAreaInfo(String sendAreaInfo) {
		this.sendAreaInfo = sendAreaInfo;
	}


	public void setRecAreaInfo(String recAreaInfo) {
		this.recAreaInfo = recAreaInfo;
	}


	@Action(value = "order_save",results = {@Result(name=SUCCESS,type = "redirect",location = "./index.html#/myhome")})
    public String orderSave(){
		String[] sendAreaInfos = sendAreaInfo.split("/");
		String[] recAreaInfos = recAreaInfo.split("/");
		Area sendArea = new Area();
		sendArea.setProvince(sendAreaInfos[0]);
		sendArea.setCity(sendAreaInfos[1]);
		sendArea.setDistrict(sendAreaInfos[2]);
		model.setSendArea(sendArea);
		Area recdArea = new Area();
		recdArea.setProvince(recAreaInfos[0]);
		recdArea.setCity(recAreaInfos[1]);
		recdArea.setDistrict(recAreaInfos[2]);
		model.setRecArea(recdArea);
		WebClient.create(Constants.BOS_MANAGEMENT_URL+"/services/orderService/order/save").type(MediaType.APPLICATION_JSON).post(model);
        return SUCCESS;
    }

}
