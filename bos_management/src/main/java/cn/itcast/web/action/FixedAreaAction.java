package cn.itcast.web.action;

import cn.itcast.crm.domain.Customer;
import cn.itcast.domain.base.FixedArea;
import cn.itcast.domain.constants.Constants;
import cn.itcast.service.FixedAreaService;
import cn.itcast.web.action.common.BaseAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;

@Controller
@Scope(value = "prototype")
@Namespace("/base")
@ParentPackage("json-default")
public class FixedAreaAction extends BaseAction<FixedArea> {
    private static final long serialVersionUID = 65589377733827245L;
    @Autowired
    private FixedAreaService fixedAreaService;

    //定区添加
    @Action(value = "/fixedArea_save", results = {
            @Result(name = SUCCESS, type = "redirect", location = "../pages/base/fixed_area.html")})
    public String save() {
        fixedAreaService.save(model);
        System.out.println(model);
        return SUCCESS;
    }

    //分页查询
    @Action(value = "/fix_area_page", results = {@Result(name = "success", type = "json")})
    public String fixAreaPageQuery() {
        PageRequest pageable = new PageRequest(page - 1, rows);
        Specification<FixedArea> specification = new Specification<FixedArea>() {
            @Override
            public Predicate toPredicate(Root<FixedArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                ArrayList<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank(model.getId())) {
                    Predicate p = cb.equal(root.get("id").as(String.class), model.getId());
                    predicates.add(p);
                }
                if (StringUtils.isNotBlank(model.getCompany())) {
                    Predicate p = cb.like(root.get("company").as(String.class), model.getCompany());
                    predicates.add(p);
                }
                if (predicates != null && !predicates.isEmpty()) {
                    Predicate[] predicates1 = new Predicate[predicates.size()];
                    query.where(predicates.toArray(predicates1));
                }
                query.orderBy(cb.asc(root.get("id").as(String.class)));
                return query.getRestriction();
            }
        };

        Page<FixedArea> pageQuery = fixedAreaService.findPageQuery(specification, pageable);
        pushValueStackToPage(pageQuery);

        return SUCCESS;
    }

    //通过webService获取所有未关联分区的客户返回给页面
    @Action(value = "fixedArea_noassociationSelect", results = {@Result(type = "json")})
    public String noAssociationSelect() {
        Collection<? extends Customer> noAssociationCustomers = WebClient.create(Constants.CRM_MANAGEMENT_URL + "/services/customerService/noAssociationCustomers").accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
        pushValueStackToPage(noAssociationCustomers);
        return SUCCESS;
    }

    @Action(value = "fixedArea_associationSelect", results = {@Result(type = "json", name = SUCCESS)})
    public String associationSelect() {
        Collection<? extends Customer> associationCustomers = WebClient.create(Constants.CRM_MANAGEMENT_URL + "/services/customerService/hasAssociationFixedAreaCustomers/" + model.getId()).accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).getCollection(Customer.class);
        pushValueStackToPage(associationCustomers);
        return SUCCESS;
    }


    private String[] customerIds;


    public void setCustomerIds(String[] customerIds) {
        this.customerIds = customerIds;
    }

    @Action(value = "fixedArea_associationCustomersToFixedArea", results = {@Result(type = "redirect", location = "../pages/base/fixed_area.html")})
    public String associationCustomersToFixedArea() {
        String join = StringUtils.join(customerIds, ",");
        WebClient.create(Constants.CRM_MANAGEMENT_URL + "/services/customerService/associationCustomersToFixedArea?customerIdStr=" + join + "&fixedAreaId=" + model.getId()).accept(MediaType.APPLICATION_JSON).put(null);
        return SUCCESS;
    }

    private Integer courierId;
    private Integer takeTimeId;

    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }

    public void setTakeTimeId(Integer takeTimeId) {
        this.takeTimeId = takeTimeId;
    }

    @Action(value = "fixedArea_associationCourierToFixedArea", results = {@Result(name = "success", type = "redirect", location = "../pages/base/fixed_area.html")})
    public String associationCourierToFixedArea() {
        String id = model.getId();
        fixedAreaService.associationCourierToFixedArea(id, courierId, takeTimeId);
        return SUCCESS;
    }
}