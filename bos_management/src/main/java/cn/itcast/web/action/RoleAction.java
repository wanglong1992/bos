package cn.itcast.web.action;

import cn.itcast.domain.system.Role;
import cn.itcast.service.RoleService;
import cn.itcast.web.action.common.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Scope(value = "prototype")
@Namespace("/")
@ParentPackage("json-default")
public class RoleAction extends BaseAction<Role> {
    private static final long serialVersionUID = -5403941803893659722L;
    @Autowired
    private RoleService roleService;

    @Action(value = "role_list", results = {@Result(type = "json")})
    public String roleList() {
        List<Role> roles = roleService.findAll();
        pushValueStackToPage(roles);
        return SUCCESS;
    }

}
