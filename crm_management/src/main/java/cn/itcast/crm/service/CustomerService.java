package cn.itcast.crm.service;

import cn.itcast.crm.domain.Customer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

public interface CustomerService {
    // 查询所有未关联客户列表
    @GET
    @Path(value = "/noAssociationCustomers")
    @Produces(value = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    List<Customer> findNoAssociationCustomers();

    // 查询已经关联到指定定区的客户列表
    @GET
    @Path(value = "/hasAssociationFixedAreaCustomers/{fixedAreaId}")
    @Consumes(value = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    List<Customer> findHasAssociationFixedAreaCustomers(@PathParam("fixedAreaId") String fixedAreaId);

    // 将客户关联到定区上 ， 将所有客户id 拼成字符串 1,2,3，使用@QueryParam实现
    @PUT
    @Path(value = "/associationCustomersToFixedArea")
    @Consumes(value = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    void associationCustomersToFixedArea(@QueryParam("customerIdStr") String customerIdStr,
                                         @QueryParam("fixedAreaId") String fixedAreaId);

    // 注册客户/激活客户
    @POST
    @Path(value = "/customer/save")
    @Consumes(value = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    void save(Customer customer);

    //根据客户手机号查找客户
    @GET
    @Path(value = "/customer/{telephone}")
    @Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    Customer findCustomerByTelethone(@PathParam("telephone") String telephone);

    //根据客户id和地址获取定区
    @GET
    @Path("/customer/findFixedAreaIdByIdAndAddress/{id}/{address}")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    String findfixedAreaIdByIdAndAddress(@PathParam("id") Integer id, @PathParam("address") String address);


    @GET
    @Path("/customer/telephoneAndPassword/{telephone}/{password}")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    Customer  findByTelephoneAndPassword(@PathParam("telephone") String telephone,@PathParam("password") String password);
}
