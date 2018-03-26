package cn.itcast.crm.dao;

import cn.itcast.crm.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findByFixedAreaId(String fixedAreaId);

    List<Customer> findByFixedAreaIdIsNull();

    @Query(value = "update Customer set fixedAreaId=null where fixedAreaId= ?")
    @Modifying
    void updateFixedAreaIdByFixedAreaId(String fixedAreaId);

    @Query("update Customer set fixedAreaId=?2 where id=?1")
    @Modifying
    void updateFixedAreaIdByFixedAreaIds(Integer id, String fixedAreaId);

    //根据客户手机查询客户
    Customer findByTelephone(String telephone);

    //根据客户id和地址获取定区
    @Query("select fixedAreaId from Customer  where  id = ?1 and  address = ?2")
    String findfixedAreaIdByIdAndAddress(Integer id, String address);

    Customer findByTelephoneAndPassword(String telephone, String password);
}
