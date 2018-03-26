package cn.itcast.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.itcast.domain.take_delivery.WayBill;

public interface WayBillRepository  extends JpaRepository<WayBill,Integer>,JpaSpecificationExecutor<WayBill>{
}
