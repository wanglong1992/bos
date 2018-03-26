package cn.itcast.crm.service.impl;

import cn.itcast.crm.dao.CustomerRepository;
import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.CustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    //返回没有被关联的客户
    @Override
    public List<Customer> findNoAssociationCustomers() {
        return customerRepository.findByFixedAreaIdIsNull();
    }

    //返回已经关联的课户
    @Override
    public List<Customer> findHasAssociationFixedAreaCustomers(String fixedAreaId) {
        return customerRepository.findByFixedAreaId(fixedAreaId);
    }

    //客户关联到定区
    @Override
    public void associationCustomersToFixedArea(String customerIdStr, String fixedAreaId) {
        // 一：使用定区id清空当前定区ID的字段
        customerRepository.updateFixedAreaIdByFixedAreaId(fixedAreaId);
        // 二：将客户重新关联到指定的定区ID的字段
        if (StringUtils.isBlank(customerIdStr) || "null".equals(customerIdStr)) {
            return;
        }
        String[] customerIdStrs = customerIdStr.split(",");
        for (String customerId : customerIdStrs) {
            Integer id = Integer.valueOf(customerId);
            customerRepository.updateFixedAreaIdByFixedAreaIds(id, fixedAreaId);
        }
    }

    //客户注册
    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    //根据客户手机查询客户
    @Override
    public Customer findCustomerByTelethone(String telephone) {
        return customerRepository.findByTelephone(telephone);
    }

    /**
     * //根据客户id和地址获取定区
     *
     * @param id
     * @param address
     * @return
     */
    @Override
    public String findfixedAreaIdByIdAndAddress(Integer id, String address) {
        return customerRepository.findfixedAreaIdByIdAndAddress(id, address);
    }

    @Override
    public Customer findByTelephoneAndPassword(String telephone, String password) {
        return customerRepository.findByTelephoneAndPassword(telephone,password);

    }
}
