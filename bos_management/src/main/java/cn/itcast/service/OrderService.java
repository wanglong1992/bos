package cn.itcast.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import cn.itcast.domain.take_delivery.Order;

public interface OrderService {
	@Path("/order/save")
	@POST
	@Consumes(value={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	void save(Order order);

	Order findByOrderNum(String orderNum);
}
