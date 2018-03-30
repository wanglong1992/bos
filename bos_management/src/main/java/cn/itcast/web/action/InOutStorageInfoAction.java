package cn.itcast.web.action;

import cn.itcast.domain.transit.InOutStorageInfo;
import cn.itcast.service.InOutStorageInfoService;
import cn.itcast.web.action.common.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@ParentPackage(value = "json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class InOutStorageInfoAction extends BaseAction<InOutStorageInfo> {
    private static final long serialVersionUID = -1546300614160196270L;
    @Autowired
    private InOutStorageInfoService inOutStorageInfoService;
    private Integer transitInfoId;

    public void setTransitInfoId(Integer transitInfoId) {
        this.transitInfoId = transitInfoId;
    }

    @Action(value = "inoutstore_save", results = {@Result(name = "success", type = "redirect", location = "pages/transit/transitinfo.html")})
    public String save() {
        inOutStorageInfoService.save(model, transitInfoId);
        return SUCCESS;
    }

}
