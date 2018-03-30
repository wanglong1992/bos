package cn.itcast.web.action;

import cn.itcast.domain.transit.TransitInfo;
import cn.itcast.service.TransitInfoService;
import cn.itcast.web.action.common.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@Controller
@Scope(value = "prototype")
@ParentPackage("json-default")
@Namespace("/")
public class TransitInfoAction extends BaseAction<TransitInfo> {
    private static final long serialVersionUID = -8509889556001134713L;
    @Autowired
    private TransitInfoService transitInfoService;


    private String wayBillIds;

    public void setWayBillIds(String wayBillIds) {
        this.wayBillIds = wayBillIds;
    }

    @Action(value = "transit_create", results = {@Result(type = "json")})
    public String create() {
        Map<String, Object> map = new HashMap<>();
        try {
            transitInfoService.createTransits(wayBillIds);
            map.put("success", true);
            map.put("msg", "开启中转成功");

        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", "开启中转失败" + e.getMessage());
        }

        pushValueStackToPage(map);
        return SUCCESS;
    }

    @Action(value = "transit_pageQuery", results = {@Result(type = "json")})
    public String pageQuery() {
        Pageable pageRequest = new PageRequest(page - 1, rows, new Sort(Sort.Direction.ASC, "id"));
        Page<TransitInfo> transitInfos = transitInfoService.findPageQuery(pageRequest);
        pushValueStackToPage(transitInfos);
        return SUCCESS;
    }
}
