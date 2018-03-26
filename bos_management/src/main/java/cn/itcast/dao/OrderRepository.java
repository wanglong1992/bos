package cn.itcast.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.itcast.domain.take_delivery.Order;

public interface OrderRepository extends JpaRepository<Order,Integer>,JpaSpecificationExecutor<Order> {

    Order findByOrderNum(String orderNum);
}
