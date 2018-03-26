package cn.itcast.web.action;

import cn.itcast.domain.base.Courier;
import cn.itcast.domain.base.Standard;
import cn.itcast.service.CourierService;
import cn.itcast.web.action.common.BaseAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@Scope(value = "prototype")
@ParentPackage("json-default")
@Namespace("/base")
@Actions
public class CourierAction extends BaseAction<Courier> {
    private static final long serialVersionUID = -6876606053461931923L;
    @Autowired
    private CourierService courierService;
    // 接受作废的快递员ids
    private String ids;
    private String fixAreaId;

    @Action(value = "courier_save", results = {
            @Result(name = SUCCESS, type = "redirect", location = "../pages/base/courier.html"), @Result(name = "error", type = "redirect", location = "../unauthorized.html")})

    public String courierAdd() {
        try {
            courierService.courierAdd(model);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    @Action(value = "courier_pageQuery", results = {@Result(name = SUCCESS, type = "json")})
    public String courierPageQuery() {
        // 无条件分页查询
        // Pageable pageable = new PageRequest(page - 1, rows, new
        // Sort("courierNum"));
        // Page<Courier> page = courierService.pageQuery(pageable);
        // pushValueStackToPage(page);

        // 条件分页查询
        PageRequest pageable = new PageRequest(page - 1, rows);
        Specification<Courier> specification = new Specification<Courier>() {
            /**
             * 传递： Root<Courier>
             * root：（连接语句的时候需要字段，获取字段的名称）代表Criteria查询的根对象，Criteria查询的查询根定义了实体类型，能为将来导航获得想要的结果，它与SQL查询中的FROM子句类似
             * CriteriaQuery<?> query：
             * （简单的查询可以使用CriteriaQuery）代表一个specific的顶层查询对象，它包含着查询的各个部分，比如：select
             * 、from、where、group by、order by等 CriteriaBuilder
             * cb：（复杂的查询可以使用CriteriaBuilder构建）用来构建CritiaQuery的构建器对象
             * 返回：Predicate：封装查询条件
             *
             */
            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = null;
                List<Predicate> list = new ArrayList<Predicate>();
                // 若有这个条件,后面的都不需要拼接了
                if (StringUtils.isNoneBlank(model.getCourierNum())) {
                    Predicate p = cb.equal(root.get("courierNum").as(String.class), model.getCourierNum());
                    list.add(p);
                } else {
                    if (StringUtils.isNotBlank(model.getCompany())) {
                        Predicate p = cb.like(root.get("company").as(String.class), "%" + model.getCompany() + "%");
                        list.add(p);
                    }
                    if (StringUtils.isNotBlank(model.getType())) {
                        Predicate p = cb.like(root.get("type").as(String.class), "%" + model.getType() + "%");
                        list.add(p);
                    }

                    Join<Courier, Standard> join = root.join("standard", JoinType.INNER);
                    if (model.getStandard() != null && model.getStandard().getId() != null) {
                        Predicate p = cb.equal(join.get("id").as(Integer.class), model.getStandard().getId());
                        list.add(p);
                    }
                }

                // 第一种方法
                // if (list != null && list.size() > 0) {
                // Predicate [] p = new Predicate[list.size()];
                // predicate = cb.and(list.toArray(p));
                // }
                // 第二种方法
                if (list != null && list.size() > 0) {
                    Predicate[] p = new Predicate[list.size()];
                    query.where(list.toArray(p));
                }

                // 排序
                query.orderBy(cb.asc(root.get("courierNum").as(String.class)));

                predicate = query.getRestriction();
                return predicate;
            }

        };

        Page<Courier> page = courierService.findPageQuery(specification, pageable);
        pushValueStackToPage(page);

        return SUCCESS;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    // 作废快递员
    @Action(value = "courier_delete", results = {
            @Result(name = SUCCESS, type = "redirect", location = "../pages/base/courier.html")})
    public String courierDelete() {
        String[] arrayIds = ids.split(",");
        courierService.deleteByIds(arrayIds);
        return SUCCESS;
    }

    //恢复快递员
    @Action(value = "courier_revoke", results = {
            @Result(name = SUCCESS, type = "redirect", location = "../pages/base/courier.html")})
    public String courierRevoke() {
        String[] arrayIds = ids.split(",");
        courierService.revokeByIds(arrayIds);
        return SUCCESS;
    }

    @Action(value = "courier_findnoassociation", results = {@Result(type = "json", name = "success")})
    public String findNoAssociation() {
        List<Courier> noAssociationourier = courierService.findCourierByfixedAreasIsNull();
        pushValueStackToPage(noAssociationourier);
        return SUCCESS;
    }

    public void setFixAreaId(String fixAreaId) {
        this.fixAreaId = fixAreaId;
    }

    @Action(value = "Courier_findCourierByFixArea", results = {@Result(type = "json")})
    public String findCourierByFixAreaId() {
        List<Courier> couriers = courierService.findCourierByFixAreaId(fixAreaId);
        pushValueStackToPage(couriers);
        return SUCCESS;
    }

}
