package cn.itcast.web.action;

import cn.itcast.domain.transit.SignInfo;
import cn.itcast.service.SignInfoService;
import cn.itcast.web.action.common.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope(value = "prototype")
@ParentPackage("json-default")
@Namespace("/")
public class SignInfoAction extends BaseAction<SignInfo> {
    private static final long serialVersionUID = -7371488130814776464L;
    @Autowired
    private SignInfoService signInfoService;
    private Integer transitInfoId;

    public void setTransitInfoId(Integer transitInfoId) {
        this.transitInfoId = transitInfoId;
    }

    @Action(value = "sign_save", results = {@Result(type = "redirect", location = "pages/transit/transitinfo.html")})
    public String save() {
        signInfoService.save(model, transitInfoId);
        return SUCCESS;
    }
}
